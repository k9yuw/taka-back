package taka.takaspring.Rental.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import taka.takaspring.Organization.db.OrgEntity;

public class RentalItemManageDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RentalItemManageRequest {

        private String orgId;

        @NotBlank(message = "물품 이름을 입력해주세요.")
        private String itemName;

        private boolean isAvailable;

        private String rentalPeriod;

        private String itemImageUrl;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalItemManageResponse {

        private String itemName;
        private String rentalPeriod;
        private boolean isAvailable;

        public RentalItemManageResponse(String itemName, String rentalPeriod, boolean isAvailable){
            this.itemName = itemName;
            this.rentalPeriod = rentalPeriod;
            this.isAvailable = isAvailable;
        }
    }

}
