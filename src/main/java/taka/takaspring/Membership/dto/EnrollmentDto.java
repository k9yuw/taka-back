package taka.takaspring.Membership.dto;

import lombok.*;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Organization.db.OrgEntity;

public class EnrollmentDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EnrollmentRequest{
        private UserEntity user;
        private OrgEntity org;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class EnrollmentResponse {


        public EnrollmentResponse() {

        }
    }
}
