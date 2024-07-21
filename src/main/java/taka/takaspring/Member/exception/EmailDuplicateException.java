package taka.takaspring.Member.exception;

import taka.takaspring.common.exception.DuplicateException;
import taka.takaspring.common.exception.ErrorCode;

public class EmailDuplicateException extends DuplicateException {

    public EmailDuplicateException(String message){
        super(message, ErrorCode.EMAIL_DUPLICATE);
    }
}

