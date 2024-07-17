package taka.takaspring.common.exception;

public class VerificationException extends CustomException {

    public VerificationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
