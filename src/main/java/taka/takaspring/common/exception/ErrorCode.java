package taka.takaspring.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 회원가입 오류
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "MEM-001", "이미 가입된 이메일입니다."),
    STUDENT_NUMBER_DUPLICATE(HttpStatus.CONFLICT, "MEM-002", "이미 가입된 학번입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "MEM-003", "회원가입 인증번호가 틀렸습니다."),
    VERIFICATION_CODE_SENDING_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "MEM-004", "인증코드 전송에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}


