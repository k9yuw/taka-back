package taka.takaspring.Rental.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Rental.db.RentalCategoryEntity;

public class RentalItemManageDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RentalItemManageRequest {

        private Long orgId;

        @NotBlank(message = "물품 이름을 입력해주세요.")
        private String itemName;

        @NotNull(message = "카테고리를 선택해주세요.")
        private Long categoryId;

        private boolean isAvailable;
        private int rentalPeriod;
        private String[] itemImageUrl;
        private String description;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalItemManageResponse {

        private String itemName;
        private String categoryName;
        private int rentalPeriod;
        private boolean isAvailable;
        private String[] itemImageUrl;
        private String description;

        public RentalItemManageResponse(String itemName, String categoryName, int rentalPeriod, boolean isAvailable, String[] itemImageUrl, String description) {
            this.itemName = itemName;
            this.categoryName = categoryName;
            this.rentalPeriod = rentalPeriod;
            this.isAvailable = isAvailable;
            this.itemImageUrl = itemImageUrl;
            this.description = description;
        }
    }
}
