package taka.takaspring.Rental.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taka.takaspring.Membership.exception.OrgEntityNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalCategoryEntity;
import taka.takaspring.Rental.db.RentalCategoryRepository;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.dto.RentalItemManageDto;
import taka.takaspring.Rental.exception.RentalItemNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalItemManageService {

    private final RentalItemRepository rentalItemRepository;
    private final RentalCategoryRepository rentalCategoryRepository;
    private final OrgRepository orgRepository;

    public RentalItemManageService(RentalItemRepository rentalItemRepository, RentalCategoryRepository rentalCategoryRepository, OrgRepository orgRepository) {
        this.rentalItemRepository = rentalItemRepository;
        this.rentalCategoryRepository = rentalCategoryRepository;
        this.orgRepository = orgRepository;
    }

    // 단체에 등록된 모든 대여 물품 조회
    @Transactional
    public List<RentalItemManageDto.RentalItemManageResponse> getRentalItemsByOrgId(Long orgId) {
        List<RentalItemEntity> rentalItemEntities = rentalItemRepository.findByOrganizationId(orgId);
        return rentalItemEntities.stream()
                .map(this::convertToRentalItemManageResponse)
                .collect(Collectors.toList());
    }

    private RentalItemManageDto.RentalItemManageResponse convertToRentalItemManageResponse(RentalItemEntity rentalItemEntity) {
        return RentalItemManageDto.RentalItemManageResponse.builder()
                .itemName(rentalItemEntity.getItemName())
                .categoryName(rentalItemEntity.getCategory().getCategoryName())
                .rentalPeriod(rentalItemEntity.getRentalPeriod())
                .isAvailable(rentalItemEntity.isAvailable())
                .build();
    }

    // 단체에 대여 물품 추가
    @Transactional
    public RentalItemManageDto.RentalItemManageResponse addRentalItem(Long orgId, RentalItemManageDto.RentalItemManageRequest request) {

        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("물품을 추가하려는 단체를 찾을 수 없습니다. id : " + orgId));

        RentalCategoryEntity category = rentalCategoryRepository.findById(request.getCategoryId())
                .filter(cat -> cat.getOrganization().equals(organization))
                .orElseThrow(() -> new IllegalArgumentException("해당 단체에 유효한 카테고리 ID가 아닙니다."));

        RentalItemEntity rentalItem = RentalItemEntity.builder()
                .itemName(request.getItemName())
                .organization(organization)
                .category(category)
                .isAvailable(true)
                .rentalPeriod(request.getRentalPeriod())
                .itemImageUrl(request.getItemImageUrl())
                .build();

        rentalItemRepository.save(rentalItem);

        return RentalItemManageDto.RentalItemManageResponse.builder()
                .itemName(rentalItem.getItemName())
                .categoryName(category.getCategoryName())
                .rentalPeriod(rentalItem.getRentalPeriod())
                .isAvailable(rentalItem.isAvailable())
                .build();
    }

    // 단체의 대여 물품 수정
    @Transactional
    public RentalItemManageDto.RentalItemManageResponse updateRentalItem(Long orgId, Long itemId, RentalItemManageDto.RentalItemManageRequest request) {

        RentalItemEntity rentalItem = rentalItemRepository.findByIdAndOrganizationId(itemId, orgId)
                .orElseThrow(() -> new RentalItemNotFoundException("수정 요청한 대여 물품을 찾을 수 없습니다. id: " + itemId));

        RentalCategoryEntity category = rentalCategoryRepository.findById(request.getCategoryId())
                .filter(cat -> cat.getOrganization().equals(rentalItem.getOrganization()))
                .orElseThrow(() -> new IllegalArgumentException("해당 단체에 유효한 카테고리 ID가 아닙니다."));

        rentalItem.updateFields(
                request.getItemName(),
                category,
                request.isAvailable(),
                request.getRentalPeriod(),
                request.getItemImageUrl(),
                request.getDescription()
        );

        RentalItemEntity updatedItem = rentalItemRepository.save(rentalItem);

        return RentalItemManageDto.RentalItemManageResponse.builder()
                .itemName(updatedItem.getItemName())
                .categoryName(updatedItem.getCategory().getCategoryName())
                .rentalPeriod(updatedItem.getRentalPeriod())
                .isAvailable(updatedItem.isAvailable())
                .build();
    }

    // 단체의 대여 물품 삭제
    @Transactional
    public void deleteRentalItem(Long orgId, Long itemId) {

        RentalItemEntity rentalItem = rentalItemRepository.findByIdAndOrganizationId(itemId, orgId)
                .orElseThrow(() -> new RentalItemNotFoundException("물품 삭제 요청한 단체 id : " + orgId + " / 삭제 요청한 대여 물품 id : " + itemId));

        rentalItemRepository.delete(rentalItem);
    }
}
