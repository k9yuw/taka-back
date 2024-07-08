package taka.takaspring.Rental.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.cglib.core.Local;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Rental.db.RentalItemEntity;

import java.time.LocalDateTime;

public class RentalDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RentalRequest{

        private UserEntity user;
        private OrgEntity org;
        private RentalItemEntity item;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RentalResponse {

        private String userName;
        private String orgName;
        private String itemName;
        private LocalDateTime rentalStartDate;
        private LocalDateTime rentalEndDate;

        public RentalResponse(String userName, String orgName, String itemName, LocalDateTime rentalStartDate, LocalDateTime rentalEndDate) {
            this.userName = userName;
            this.orgName = orgName;
            this.itemName = itemName;
            this.rentalStartDate = rentalStartDate;
            this.rentalEndDate = rentalEndDate;
        }
    }

}
