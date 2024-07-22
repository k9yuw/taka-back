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

        private RentalRecordEntity rentalRecord;

        public RentalRecordResponse(RentalRecordEntity rentalRecord) {
            this.rentalRecord = rentalRecord;
        }
    }

}
