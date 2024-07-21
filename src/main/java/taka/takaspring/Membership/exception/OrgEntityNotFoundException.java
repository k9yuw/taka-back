package taka.takaspring.Membership.exception;

import org.hibernate.annotations.NotFound;
import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class OrgEntityNotFoundException extends NotFoundException {
    public OrgEntityNotFoundException(String message){
        super(message, ErrorCode.ORG_NOT_FOUND);
    }
}
