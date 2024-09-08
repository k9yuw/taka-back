package taka.takaspring.Rental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class RentalItemDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RentalItemRequest {

        private Long id;
        private Long orgId;
        private Long categoryId;
        private String itemName;
        private boolean isAvailable;
        private int rentalPeriod;
        private String[] photos;
        private String[] descriptions;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalItemResponse {

        private String itemName;
        private String categoryName;
        private String rentalPeriod;
        private boolean isAvailable;

        public RentalItemResponse(String itemName, String categoryName, String rentalPeriod, boolean isAvailable) {
            this.itemName = itemName;
            this.categoryName = categoryName;
            this.rentalPeriod = rentalPeriod;
            this.isAvailable = isAvailable;
        }
    }
}
