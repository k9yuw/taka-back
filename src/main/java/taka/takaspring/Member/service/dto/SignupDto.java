package taka.takaspring.Member.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import taka.takaspring.Member.db.UserEntity;

public class SignupDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SignupRequest {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 5, max = 20)
        @Pattern(regexp = "^[a-zA-z0-9]*$", message = "아이디는 영어와 숫자만 가능합니다.")
        private String userId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20)
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대, 소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 1, max = 20)
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바르지 않은 이메일 형식입니다.")
        private String email;

        @NotBlank(message = "전화번호를 입력해주세요.")
        private String phoneNumber;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpResponse {

        private String userId;
        private String email;

        @Builder
        public SignUpResponse(UserEntity user){
            this.userId = user.getUserId();
            this.email = user.getEmail();
        }
    }

}
