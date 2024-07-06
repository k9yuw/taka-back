package taka.takaspring.Member.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.enums.RoleType;

public class SignupDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SignupRequest {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바르지 않은 이메일 형식입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@korea\\.ac\\.kr$", message = "이메일은 korea.ac.kr 도메인만 허용됩니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20)
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대, 소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 1, max = 20)
        private String name;

        @NotBlank(message = "전공을 입력해주세요.")
        private String major;

        @NotBlank(message = "학번을 입력해주세요.")
        private String studentNum;

        @NotBlank(message = "전화번호를 입력해주세요.")
        private String phoneNumber;

        private RoleType role;

        private String verificationCode;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpResponse {

        private String email;
        private String name;
        private String major;
        private String studentNum;
        private String phoneNumber;

        @Builder
        public SignUpResponse(UserEntity user){
            this.email = user.getEmail();
            this.name = user.getName();
            this.major = user.getMajor();
            this.studentNum = user.getStudentNum();
            this.phoneNumber = user.getPhoneNumber();
        }
    }

}
