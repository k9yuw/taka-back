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
        private String department;
        private String orgDescription;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrgResponse{

        private String orgName;
        private String department;
        private String orgDescription;

        public OrgResponse(String orgName, String department, String orgDescription){
            this.orgName = orgName;
            this.department = department;
            this.orgDescription = orgDescription;
        }

    }
}
