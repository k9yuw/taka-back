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
        private String rentalPeriod;
        private String itemImageUrl;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalItemManageResponse {

        private String itemName;
        private String categoryName;
        private String rentalPeriod;
        private boolean isAvailable;

        public RentalItemManageResponse(String itemName, String categoryName, String rentalPeriod, boolean isAvailable) {
            this.itemName = itemName;
            this.categoryName = categoryName;
            this.rentalPeriod = rentalPeriod;
            this.isAvailable = isAvailable;
        }
    }
}
