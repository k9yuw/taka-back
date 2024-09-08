package taka.takaspring.Member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class sendVerificationCodeDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(description = "인증 코드 전송을 요청하는 DTO")
    public static class Request {

        @Schema(description = "사용자의 이메일", example = "test@korea.ac.kr")
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바르지 않은 이메일 형식입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@korea\\.ac\\.kr$", message = "이메일은 korea.ac.kr 도메인만 허용됩니다.")
        private String email;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "인증 코드 전송에 대한 응답 DTO")
    public static class Response {

        @Schema(description = "사용자의 이메일", example = "test@korea.ac.kr")
        private String email;

        @Schema(description = "응답 메시지", example = "코드 전송이 성공되었습니다.")
        private String message;

        @Builder
        public Response(String email, String message) {
            this.email = email;
            this.message = message;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "에러 발생 시 응답 DTO")
    public static class ErrorResponse {

        @Schema(description = "에러 코드", example = "409")
        private String errorCode;

        @Schema(description = "에러 메시지", example = "이미 회원가입이 되어 있는 이메일입니다.")
        private String errorMessage;

        @Builder
        public ErrorResponse(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }
}


