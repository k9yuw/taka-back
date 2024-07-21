package taka.takaspring.Membership.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class MembershipNotFoundException extends NotFoundException {
    public MembershipNotFoundException(String message){
        super(message, ErrorCode.MEMBERSHIP_NOT_FOUND);
    }
}
