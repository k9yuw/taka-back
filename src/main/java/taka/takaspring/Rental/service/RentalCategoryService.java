package taka.takaspring.Rental.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import taka.takaspring.Membership.exception.OrgEntityNotFoundException;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;
import taka.takaspring.Rental.db.RentalCategoryEntity;
import taka.takaspring.Rental.db.RentalCategoryRepository;
import taka.takaspring.Rental.dto.RentalCategoryDto;
import taka.takaspring.Rental.exception.CategoryNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalCategoryService {

    private final OrgRepository orgRepository;
    private final RentalCategoryRepository rentalCategoryRepository;

    public RentalCategoryService(OrgRepository orgRepository, RentalCategoryRepository rentalCategoryRepository) {
        this.orgRepository = orgRepository;
        this.rentalCategoryRepository = rentalCategoryRepository;
    }

    @Transactional
    public RentalCategoryDto.CategoryResponse createCategory(Long orgId, RentalCategoryDto.CategoryRequest request) {
        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("카테고리 생성 요청한 단체 id : " + orgId));

        RentalCategoryEntity category = RentalCategoryEntity.builder()
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .organization(organization)
                .build();

        RentalCategoryEntity savedCategory = rentalCategoryRepository.save(category);

        return new RentalCategoryDto.CategoryResponse(
                savedCategory.getId(),
                savedCategory.getCategoryName(),
                savedCategory.getDescription(),
                organization.getOrgName()
        );
    }

    @Transactional
    public List<RentalCategoryDto.CategoryResponse> getCategoriesByOrgId(Long orgId) {
        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("카테고리 조회 요청한 단체 id : " + orgId));

        List<RentalCategoryEntity> categories = rentalCategoryRepository.findByOrganization(organization);

        return categories.stream()
                .map(category -> new RentalCategoryDto.CategoryResponse(
                        category.getId(),
                        category.getCategoryName(),
                        category.getDescription(),
                        organization.getOrgName()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public RentalCategoryDto.CategoryResponse updateCategory(Long orgId, Long categoryId, RentalCategoryDto.CategoryRequest request) {
        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("카테고리 수정 요청한 단체 id : " + orgId));

        RentalCategoryEntity category = rentalCategoryRepository.findByIdAndOrganization(categoryId, organization)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리 id: " + categoryId + " 단체 id: " + orgId));

        category.updateCategory(request.getCategoryName(), request.getDescription());

        RentalCategoryEntity updatedCategory = rentalCategoryRepository.save(category);

        return new RentalCategoryDto.CategoryResponse(
                updatedCategory.getId(),
                updatedCategory.getCategoryName(),
                updatedCategory.getDescription(),
                organization.getOrgName()
        );
    }

    @Transactional
    public void deleteCategory(Long orgId, Long categoryId) {
        OrgEntity organization = orgRepository.findById(orgId)
                .orElseThrow(() -> new OrgEntityNotFoundException("카테고리 삭제 요청한 단체 id : " + orgId));

        RentalCategoryEntity category = rentalCategoryRepository.findByIdAndOrganization(categoryId, organization)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리 id: " + categoryId + " 단체 id: " + orgId));

        rentalCategoryRepository.delete(category);
    }
}
