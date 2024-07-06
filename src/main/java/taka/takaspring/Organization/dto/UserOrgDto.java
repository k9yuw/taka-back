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
    public class UserByOrgResponse{

        private String name;
        private String major;
        private String studentNum;

        @Builder
        public UserByOrgResponse(UserOrgEntity userOrgEntity){
            this.name = userOrgEntity.getUser().getName();
            this.major = userOrgEntity.getUser().getMajor();
            this.studentNum = userOrgEntity.getUser().getStudentNum();
        }

    }
}
