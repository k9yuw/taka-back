package taka.takaspring.Member.dto;

import jakarta.persistence.Column;
import lombok.*;

public class VerifyUserDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        private String accessToken;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long id;
        private String email;
        private String password;
        private String name;
        private String major;
        private String studentNum;
        private String phoneNumber;
        private String profileImageUrl;

        @Builder
        public Response(Long id, String email, String password, String name, String major, String studentNum, String phoneNumber, String profileImageUrl) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.name = name;
            this.major = major;
            this.studentNum = studentNum;
            this.phoneNumber = phoneNumber;
            this.profileImageUrl = profileImageUrl;
        }
    }


}
