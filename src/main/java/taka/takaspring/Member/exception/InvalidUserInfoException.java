package taka.takaspring.Member.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.VerificationException;

public class InvalidUserInfoException extends VerificationException {

    public InvalidUserInfoException(String message){
        super(message, ErrorCode.INVALID_USERINFO);
    }

}
