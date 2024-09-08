package taka.takaspring.Rental.service;


import org.springframework.stereotype.Service;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.dto.CategoryDto;
import taka.takaspring.Rental.dto.RentalItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalItemService {

    private final RentalItemRepository rentalItemRepository;

    public RentalItemService(RentalItemRepository rentalItemRepository) {
        this.rentalItemRepository = rentalItemRepository;
    }

    // 카테고리 리스트 가져오기
    public List<CategoryDto> getCategoryListByOrgId(Long orgId) {
        List<RentalItemEntity> rentalItemEntities = rentalItemRepository.findByOrganizationId(orgId);

        return rentalItemEntities.stream()
                .map(item -> CategoryDto.builder()
                        .id(item.getCategory().getId())
                        .name(item.getCategory().getCategoryName())
                        .description(item.getCategory().getDescription())
                        .build())
                .distinct()
                .collect(Collectors.toList());
    }

    // 아이템 리스트 가져오기
    public List<RentalItemDto.RentalItemResponse> getRentalItemListByOrgId(Long orgId) {
        List<RentalItemEntity> rentalItemEntities = rentalItemRepository.findByOrganizationId(orgId);

        return rentalItemEntities.stream()
                .map(item -> RentalItemDto.RentalItemResponse.builder()
                        .id(item.getId())
                        .orgId(item.getOrganization().getId())
                        .categoryId(item.getCategory().getId())
                        .name(item.getItemName())
                        .isAvailable(item.isAvailable())
                        .rentalPeriod(item.getRentalPeriod())
                        .photos(item.getItemImageUrl())
                        .description(item.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}

