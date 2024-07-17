package taka.takaspring.common;

import lombok.Getter;
import taka.takaspring.common.exception.ErrorCode;

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
