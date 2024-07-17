package taka.takaspring.Member.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.VerificationException;

public class InvalidVerificationCodeException extends VerificationException {

    public InvalidVerificationCodeException(String message, ErrorCode errorCode){
        super(message, ErrorCode.INVALID_VERIFICATION_CODE);
    }

}
