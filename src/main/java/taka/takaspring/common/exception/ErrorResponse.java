package taka.takaspring.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int httpCode;
    private final String errorCode;
    private final String message;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.httpCode = errorCode.getHttpStatus().value();
        this.errorCode = errorCode.name();
        this.message = message;
    }

}
