package taka.takaspring.Rental.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.dto.RentalItemManageDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalItemManageService {

    private final RentalItemRepository rentalItemRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public RentalItemManageService(RentalItemRepository rentalItemRepository, OrgRepository orgRepository) {
        this.rentalItemRepository = rentalItemRepository;
        this.orgRepository = orgRepository;
    }

    @Transactional
    public List<RentalItemManageDto.RentalItemManageResponse> getRentalItemsByOrgId(Long orgId){
        List<RentalItemEntity> rentalItemEntities = rentalItemRepository.findByOrganizationId(orgId);
        return rentalItemEntities.stream()
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
    public RentalItemManageDto.RentalItemManageResponse addRentalItem(Long orgId, RentalItemManageDto.RentalItemManageRequest request) {

        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 단체입니다."));

        RentalItemEntity rentalItem = RentalItemEntity.builder()
                .itemName(request.getItemName())
                .organization(organization)
                .isAvailable(true)
                .rentalPeriod(request.getRentalPeriod())
                .itemImageUrl(request.getItemImageUrl())
                .build();

        RentalItemManageDto.RentalItemManageResponse response = RentalItemManageDto.RentalItemManageResponse.builder().
                    itemName(request.getItemName()).
                    rentalPeriod(request.getRentalPeriod()).
                    build();

        rentalItemRepository.save(rentalItem);

        return response;
    }


    @Transactional
    public RentalItemEntity updateRentalItem(Long orgId, Long itemId, RentalItemManageDto.RentalItemManageRequest request) {
        RentalItemEntity rentalItem = rentalItemRepository.findByIdAndOrganizationId(itemId, orgId)
                .orElseThrow(() -> new EntityNotFoundException("해당 물품이 존재하지 않습니다."));

        rentalItem.updateFields(request.getItemName(), request.isAvailable(), request.getRentalPeriod(), request.getItemImageUrl());

        return rentalItemRepository.save(rentalItem);
    }

    @Transactional
    public void deleteRentalItem(Long organizationId, Long itemId) {
        RentalItemEntity rentalItem = rentalItemRepository.findByIdAndOrganizationId(itemId, organizationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 물품이 존재하지 않습니다."));

        rentalItemRepository.delete(rentalItem);
    }
}
