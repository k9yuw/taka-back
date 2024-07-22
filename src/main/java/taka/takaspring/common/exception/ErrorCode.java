package taka.takaspring.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 회원가입 오류
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "MEM-001", "이미 가입된 이메일입니다."),
    STUDENT_NUMBER_DUPLICATE(HttpStatus.CONFLICT, "MEM-002", "이미 가입된 학번입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "MEM-003", "회원가입 인증번호가 틀렸습니다."),
    VERIFICATION_CODE_SENDING_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "MEM-004", "인증코드 전송에 실패했습니다."),

    // NotFoundException
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "NTF-001", "존재하지 않는 사용자입니다."),
    ORG_NOT_FOUND(HttpStatus.NOT_FOUND, "NTF-002", "존재하지 않는 단체입니다."),
    ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "NTF-003", "존재하지 않는 가입 요청입니다."),
    MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "NTF-004", "존재하지 않는 입부 내역입니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "NTF-005", "존재하지 않는 대여 물품입니다."),
    RENTAL_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "NTF-006", "존재하지 않는 대여 기록입니다."),


    // Rental 오류
    ITEM_ALREADY_RENT(HttpStatus.CONFLICT,"RNT-001", "물품이 현재 대여 중입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}


