package taka.takaspring.Membership.dto;

import lombok.*;

public class MembershipDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserByOrgRequest{
        private Long orgId;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class UserByOrgResponse {
        private String name;
        private String major;
        private String studentNum;

        public UserByOrgResponse(String name, String major, String studentNum) {
            this.name = name;
            this.major = major;
            this.studentNum = studentNum;
        }
    }
}
