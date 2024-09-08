package taka.takaspring.Rental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

public class RentalItemDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalItemResponse {
        private Long id;
        private Long orgId;
        private Long categoryId;
        private String name;
        private boolean isAvailable;
        private int rentalPeriod;
        private String[] photos;
        private String description;

        public RentalItemResponse(Long id, Long orgId, Long categoryId, String name, boolean isAvailable, int rentalPeriod, String[] photos, String description) {
            this.id = id;
            this.orgId = orgId;
            this.categoryId = categoryId;
            this.name = name;
            this.isAvailable = isAvailable;
            this.rentalPeriod = rentalPeriod;
            this.photos = photos;
            this.description = description;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RentalItemsWithCategoriesResponse {
        private List<CategoryDto> category;
        private List<RentalItemResponse> item;
    }

}
