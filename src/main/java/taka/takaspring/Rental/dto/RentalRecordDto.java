package taka.takaspring.Rental.dto;

import lombok.*;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.db.RentalRecordEntity;

import java.time.LocalDateTime;

public class RentalRecordDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RentalRecordRequest{

        private RentalRecordEntity rentalRecord;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalRecordResponse {

        private String userName;
        private String orgName;
        private String itemName;
        private LocalDateTime rentalStartDate;
        private LocalDateTime returnDate;

        public RentalRecordResponse(String userName, String orgName, String itemName, LocalDateTime rentalStartDate, LocalDateTime returnDate) {
            this.userName = userName;
            this.orgName = orgName;
            this.itemName = itemName;
            this.rentalStartDate = rentalStartDate;
            this.returnDate = returnDate;
        }
    }

}
