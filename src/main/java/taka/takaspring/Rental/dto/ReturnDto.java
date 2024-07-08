package taka.takaspring.Rental.dto;

import lombok.*;
import taka.takaspring.Rental.db.RentalRecordEntity;

import java.time.LocalDateTime;

public class ReturnDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ReturnRequest{
        private RentalRecordEntity rentalRecord;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ReturnResponse {

        private String userName;
        private String orgName;
        private String itemName;
        private LocalDateTime rentalStartDate;
        private LocalDateTime returnDate;
        private boolean isReturned;

        public ReturnResponse(String userName, String orgName, String itemName, LocalDateTime rentalStartDate, LocalDateTime returnDate, boolean isReturned) {
            this.userName = userName;
            this.orgName = orgName;
            this.itemName = itemName;
            this.rentalStartDate = rentalStartDate;
            this.returnDate = returnDate;
            this.isReturned = isReturned;
        }
    }
}
