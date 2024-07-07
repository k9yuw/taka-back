package taka.takaspring.Rental.dto;

import lombok.*;

public class RentalItemManageDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RentalItemManageRequest {


    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RentalItemManageResponse {



        @Builder
        public RentalItemRegiByOrgAdminResponse(){

        }
    }

}
