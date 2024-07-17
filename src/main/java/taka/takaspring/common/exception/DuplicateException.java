package taka.takaspring.common.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends CustomException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}

