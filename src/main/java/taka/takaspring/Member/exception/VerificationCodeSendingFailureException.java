package taka.takaspring.Member.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.VerificationException;

public class VerificationCodeSendingFailureException extends VerificationException {

    public VerificationCodeSendingFailureException(String message){
        super(message, ErrorCode.VERIFICATION_CODE_SENDING_FAILURE);
    }
}
