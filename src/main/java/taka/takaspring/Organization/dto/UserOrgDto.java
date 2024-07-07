package taka.takaspring.Organization.dto;

import lombok.*;
import taka.takaspring.Organization.db.UserOrgEntity;

public class UserOrgDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserByOrgRequest{

        private Long orgId;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public class UserByOrgResponse {
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
