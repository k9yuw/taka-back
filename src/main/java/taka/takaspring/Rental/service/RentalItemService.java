package taka.takaspring.Rental.service;


import org.springframework.stereotype.Service;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalItemRepository;
import taka.takaspring.Rental.dto.RentalItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalItemService {

    private final RentalItemRepository rentalItemRepository;

    public RentalItemService(RentalItemRepository rentalItemRepository) {
        this.rentalItemRepository = rentalItemRepository;
    }

    public List<RentalItemDto.RentalItemResponse> getRentalItemListByOrgId(Long orgId) {
        List<RentalItemEntity> rentalItemEntities = rentalItemRepository.findByOrganizationId(orgId);

        return rentalItemEntities.stream()
                .map(item -> RentalItemDto.RentalItemResponse.builder()
                        .id(item.getId())
                        .orgId(item.getOrg().getId())
                        .name(item.getName())
                        .isAvailable(item.isAvailable())
                        .rentalPeriod(item.getRentalPeriod())
                        .photos(item.getPhotos())
                        .category(CategoryDto.CategoryResponse.builder()
                                .id(item.getCategory().getId())
                                .name(item.getCategory().getName())
                                .build())
                        .descriptions(item.getDescriptions())
                        .build())
                .collect(Collectors.toList());
    }
}