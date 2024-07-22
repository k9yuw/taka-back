package taka.takaspring.Rental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Membership.exception.OrgEntityNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.dto.RentalItemManageDto;
import taka.takaspring.Rental.exception.RentalItemNotFoundException;

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

    // 단체에 등록된 모든 대여 물품 조회
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

    // 단체에 대여 물품 추가
    @Transactional
    public RentalItemManageDto.RentalItemManageResponse addRentalItem(Long orgId, RentalItemManageDto.RentalItemManageRequest request) {

        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("물품 삭제 요청한 단체 id : " + orgId));

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

    // 단체의 대여 물품 수정
    @Transactional
    public RentalItemEntity updateRentalItem(Long orgId, Long itemId, RentalItemManageDto.RentalItemManageRequest request) {

        String itemName = request.getItemName();

        RentalItemEntity rentalItem = rentalItemRepository.findByIdAndOrganizationId(itemId, orgId)
                .orElseThrow(() -> new RentalItemNotFoundException("수정 요청한 대여 물품 이름 : " + itemName));

        rentalItem.updateFields(request.getItemName(), request.isAvailable(), request.getRentalPeriod(), request.getItemImageUrl());

        return rentalItemRepository.save(rentalItem);
    }

    // 단체의 대여 물품 삭제
    @Transactional
    public void deleteRentalItem(Long orgId, Long itemId) {

        RentalItemEntity rentalItem = rentalItemRepository.findByIdAndOrganizationId(itemId, orgId)
                .orElseThrow(() -> new RentalItemNotFoundException("물품 삭제 요청한 단체 id : " + orgId + " / 삭제 요청한 대여 물품 id : " + itemId));

        rentalItemRepository.delete(rentalItem);
    }
}
