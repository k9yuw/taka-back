package taka.takaspring.Rental.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.exception.OrgEntityNotFoundException;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.db.RentalRecordEntity;
import taka.takaspring.Rental.db.RentalRecordRepository;
import taka.takaspring.Rental.dto.RentalDto;
import taka.takaspring.Rental.dto.RentalItemManageDto;
import taka.takaspring.Rental.dto.ReturnDto;
import taka.takaspring.Rental.exception.ItemAlreadyRentException;
import taka.takaspring.Rental.exception.RentalItemNotFoundException;
import taka.takaspring.Rental.exception.RentalRecordNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRecordRepository rentalRecordRepository;
    private final RentalItemRepository rentalItemRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public RentalService(RentalRecordRepository rentalRecordRepository, RentalItemRepository rentalItemRepository, UserRepository userRepository, OrgRepository orgRepository) {
        this.rentalRecordRepository = rentalRecordRepository;
        this.rentalItemRepository = rentalItemRepository;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
    }

    @Transactional
    public List<RentalItemManageDto.RentalItemManageResponse> getRentalItemListByOrgId(Long orgId){
        List<RentalItemEntity> rentalItemEntityList = rentalItemRepository.findByOrganizationId(orgId);
        return rentalItemEntityList.stream()
                .map(this::convertToRentalItemManageResponse)
                .collect(Collectors.toList());
    }

    private RentalItemManageDto.RentalItemManageResponse convertToRentalItemManageResponse(RentalItemEntity rentalItemEntity) {

        RentalItemManageDto.RentalItemManageResponse response = RentalItemManageDto.RentalItemManageResponse.builder().
                itemName(rentalItemEntity.getItemName()).
                rentalPeriod(rentalItemEntity.getRentalPeriod()).
                isAvailable(rentalItemEntity.isAvailable()).
                build();

        return response;
    }

    @Transactional
    public RentalDto.RentalResponse rentItem(RentalDto.RentalRequest request) {

        Long userId = request.getUser().getId();
        Long orgId = request.getOrg().getId();
        Long itemId = request.getItem().getId();

        String userName = request.getUser().getName();
        String orgName = request.getOrg().getOrgName();
        String itemName = request.getItem().getItemName();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserEntityNotFoundException("대여 요청 한 사용자: " + userName));

        OrgEntity org = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("대여 요청 한 단체: " + orgName));

        RentalItemEntity item = rentalItemRepository.findById(itemId)
                .orElseThrow(() -> new RentalItemNotFoundException("대여 요청 한 물품: " + itemName));

        if (!item.isAvailable()) {
            throw new ItemAlreadyRentException("대여 요청 한 단체: " + orgName + " / 대여 요청 한 물품: " + itemName);
        }

        item.setAvailable(false);

        // rentalPeriod는 일 수로 입력받아야 함
        String rentalPeriod = item.getRentalPeriod();
        int days = Integer.parseInt(rentalPeriod);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime returnDate = startDate.plusDays(days);

        RentalRecordEntity rentalRecord = new RentalRecordEntity(user, item, startDate, returnDate);
        rentalRecordRepository.save(rentalRecord);

        RentalDto.RentalResponse response = RentalDto.RentalResponse.builder().
                userName(user.getName()).
                orgName(org.getOrgName()).
                itemName(item.getItemName()).
                build();

        return response;
    }

    @Transactional
    public ReturnDto.ReturnResponse returnItem(ReturnDto.ReturnRequest request) {

        Long userId = request.getRentalRecord().getUser().getId();
        Long rentalRecordId = request.getRentalRecord().getId();

        String userName = request.getRentalRecord().getUser().getName();
        String itemName = request.getRentalRecord().getItem().getItemName();


        Optional<RentalRecordEntity> optionalRentalRecord = rentalRecordRepository.findByIdAndUserId(rentalRecordId, userId);

        RentalRecordEntity rentalRecord = optionalRentalRecord
                .orElseThrow(() -> new RentalRecordNotFoundException("대여자 : " + userName + " / 대여물품 : " + itemName));

        rentalRecord.markAsReturned();
        rentalRecord.getItem().setAvailable(true);

        rentalRecordRepository.save(rentalRecord);

        ReturnDto.ReturnResponse response = ReturnDto.ReturnResponse.builder()
                .userName(rentalRecord.getUser().getName())
                .itemName(rentalRecord.getItem().getItemName())
                .rentalStartDate(rentalRecord.getRentalStartDate())
                .returnDate(LocalDateTime.now())
                .isReturned(rentalRecord.isReturned())
                .build();

        return response;
    }

    @Transactional
    public RentalDto.RentalResponse getRentalRecords(Long rentalRecordId, Long userId) {
        Optional<RentalRecordEntity> optionalRecordEntity = rentalRecordRepository.findByIdAndUserId(rentalRecordId, userId);
        RentalRecordEntity recordEntity = optionalRecordEntity.get();

        RentalDto.RentalResponse response = RentalDto.RentalResponse.builder().
                userName(recordEntity.getUser().getName()).
                itemName(recordEntity.getItem().getItemName()).
                rentalStartDate(recordEntity.getRentalStartDate()).
                returnDate(recordEntity.getReturnDate()).
                build();

        return response;
    }

    @Transactional
    public List<RentalDto.RentalResponse> getCurrentRentalsByUserId(Long userId){
        List<RentalRecordEntity> currentRentalList = rentalRecordRepository.findByUserIdIdAndIsReturnedFalse(userId);
        return currentRentalList.stream()
                .map(this::convertToRentalResponse)
                .collect(Collectors.toList());
    }

    private RentalDto.RentalResponse convertToRentalResponse(RentalRecordEntity rentalRecordEntity) {

        Optional<RentalItemEntity> optionalRentalItemEntity = rentalItemRepository.findById(rentalRecordEntity.getItem().getId());
        RentalItemEntity rentalItemEntity = optionalRentalItemEntity.get();
        String orgName = rentalItemEntity.getOrganization().getOrgName();

        RentalDto.RentalResponse response = RentalDto.RentalResponse.builder().
                userName(rentalRecordEntity.getUser().getName()).
                orgName(orgName).
                itemName(rentalRecordEntity.getItem().getItemName()).
                rentalStartDate(rentalRecordEntity.getRentalStartDate()).
                rentalEndDate(rentalRecordEntity.getRentalEndDate()).
                build();

        return response;
    }

}
