package taka.takaspring.Membership.exception;

import jakarta.persistence.EntityNotFoundException;
import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class UserEntityNotFoundException extends NotFoundException {

    public UserEntityNotFoundException(String message){
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
