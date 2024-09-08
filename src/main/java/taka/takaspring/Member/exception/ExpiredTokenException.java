package taka.takaspring.Member.exception;

import taka.takaspring.common.exception.CustomException;
import taka.takaspring.common.exception.ErrorCode;

public class ExpiredTokenException extends CustomException {
    public ExpiredTokenException(String message){
        super(message, ErrorCode.EXPIRED_TOKEN);
    }
}
