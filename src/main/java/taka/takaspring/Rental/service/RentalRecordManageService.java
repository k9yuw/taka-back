package taka.takaspring.Rental.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.db.RentalRecordEntity;
import taka.takaspring.Rental.db.RentalRecordRepository;
import taka.takaspring.Rental.dto.RentalItemManageDto;
import taka.takaspring.Rental.dto.RentalRecordDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalRecordManageService {

    private final RentalRecordRepository rentalRecordRepository;
    private final RentalItemRepository rentalItemRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public RentalRecordManageService (RentalRecordRepository rentalRecordRepository, RentalItemRepository rentalItemRepository, UserRepository userRepository, OrgRepository orgRepository) {
        this.rentalRecordRepository = rentalRecordRepository;
        this.rentalItemRepository = rentalItemRepository;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
    }

    @Transactional
    public List<RentalRecordDto.RentalRecordResponse> getRentalRecordsByOrgId(Long orgId){
        List<RentalRecordEntity> rentalRecordEntityList = rentalRecordRepository.findByOrgId(orgId);
        return rentalRecordEntityList.stream()
                .map(this::convertToRentalRecordResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RentalRecordDto.RentalRecordResponse> getRentalRecordsByItemId(Long itemId){
        List<RentalRecordEntity> rentalRecordEntityList = rentalRecordRepository.findByItemId(itemId);
        return rentalRecordEntityList.stream()
                .map(this::convertToRentalRecordResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RentalRecordDto.RentalRecordResponse> getRentalRecordsByOrgIdAndUserId(Long orgId, Long userId){
        List<RentalRecordEntity> rentalRecordEntityList = rentalRecordRepository.findByOrgIdAndUserId(orgId, userId);
        return rentalRecordEntityList.stream()
                .map(this::convertToRentalRecordResponse)
                .collect(Collectors.toList());
    }

    private RentalRecordDto.RentalRecordResponse convertToRentalRecordResponse(RentalRecordEntity rentalRecordEntity) {
        RentalRecordDto.RentalRecordResponse response = RentalRecordDto.RentalRecordResponse.builder().
                rentalRecord(rentalRecordEntity).
                build();
        return response;
    }
}
