package taka.takaspring.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends CustomException{

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
