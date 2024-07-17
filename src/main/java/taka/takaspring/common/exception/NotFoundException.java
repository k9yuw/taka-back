package taka.takaspring.common.exception;

public class NotFoundException extends CustomException{

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
