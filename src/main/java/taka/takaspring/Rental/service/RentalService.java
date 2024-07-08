package taka.takaspring.Rental.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.db.RentalRecordEntity;
import taka.takaspring.Rental.db.RentalRecordRepository;
import taka.takaspring.Rental.dto.RentalDto;
import taka.takaspring.Rental.dto.ReturnDto;

import java.time.LocalDateTime;
import java.util.Optional;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

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
    public RentalDto.RentalResponse rentItem(RentalDto.RentalRequest request) {

        Long userId = request.getUser().getId();
        Long orgId = request.getOrg().getId();
        Long itemId = request.getItem().getId();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        OrgEntity org = orgRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 단체입니다."));

        RentalItemEntity item = rentalItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 물품입니다."));

        if (!item.isAvailable()) {
            throw new IllegalStateException("물품이 현재 대여 중입니다.");
        }

        item.setAvailable(false);

        // rentalPeriod는 일 수로 입력받아야 함
        String rentalPeriod = item.getRentalPeriod();
        int days = Integer.parseInt(rentalPeriod);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime returnDate = startDate.plusDays(days);

        RentalRecordEntity rentalRecord = new RentalRecordEntity(user, org, item, startDate, returnDate);
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

        Optional<RentalRecordEntity> optionalRentalRecord = rentalRecordRepository.findByIdAndUserId(rentalRecordId, userId);

        RentalRecordEntity rentalRecord = optionalRentalRecord
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 대여기록입니다."));

        rentalRecord.markAsReturned();
        rentalRecord.getItem().setAvailable(true);

        rentalRecordRepository.save(rentalRecord);

        ReturnDto.ReturnResponse response = ReturnDto.ReturnResponse.builder()
                .userName(rentalRecord.getUser().getName())
                .orgName(rentalRecord.getOrganization().getOrgName())
                .itemName(rentalRecord.getItem().getItemName())
                .rentalStartDate(rentalRecord.getRentalStartDate())
                .returnDate(LocalDateTime.now())
                .isReturned(rentalRecord.isReturned())
                .build();

        return response;
    }

    @Transactional(readOnly = true)
    public List<RentalRecordEntity> getRentalRecords(Long userId, Long orgId) {
        return rentalRecordRepository.findByUserIdAndItemOrganizationId(userId, orgId);
    }

}
