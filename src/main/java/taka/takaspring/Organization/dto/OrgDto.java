package taka.takaspring.Organization.dto;

import lombok.*;
import taka.takaspring.Member.db.UserEntity;

public class OrgDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OrgRequest{

        private String orgName;
        private UserEntity orgAdmin;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrgResponse{

        private String orgName;
        private UserEntity orgAdmin;

        public OrgResponse(String orgName, UserEntity orgAdmin){
            this.orgName = orgName;
            this.orgAdmin = orgAdmin;
        }

    }
}
