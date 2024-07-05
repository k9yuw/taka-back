package taka.takaspring.Organization.dto;

import lombok.*;
import taka.takaspring.Member.db.UserEntity;

public class OrgRegiDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OrgRegiRequest{

        private String orgName;
        private UserEntity orgAdmin;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrgRegiResponse{

        private String orgName;
        private UserEntity orgAdmin;

        public OrgRegiResponse(String orgName, UserEntity orgAdmin){
            this.orgName = orgName;
            this.orgAdmin = orgAdmin;
        }

    }
}
