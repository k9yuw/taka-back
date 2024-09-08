package taka.takaspring.Member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class VerifyCodeDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class VerifyCodeRequest {

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바르지 않은 이메일 형식입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@korea\\.ac\\.kr$", message = "이메일은 korea.ac.kr 도메인만 허용됩니다.")
        private String email;

        @NotBlank(message = "인증번호를 입력해주세요.")
        private String verificationCode;

    }
}
