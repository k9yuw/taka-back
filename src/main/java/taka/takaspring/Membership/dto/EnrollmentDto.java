package taka.takaspring.Membership.dto;

import lombok.*;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Membership.db.MembershipEntity;
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
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EnrollmentIntermediateRequest{
        private MembershipEntity membership;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class EnrollmentResponse {

        private String userName;
        private String orgName;
        private MembershipEntity.MembershipStatus status;

        public EnrollmentResponse(String userName, String orgName, MembershipEntity.MembershipStatus status) {
            this.userName = userName;
            this.orgName = orgName;
            this.status = status;
        }
    }
}
