package taka.takaspring.Membership.exception;

import org.hibernate.annotations.NotFound;
import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class EnrollmentNotFoundException extends NotFoundException {
    public EnrollmentNotFoundException(String message){
        super(message, ErrorCode.ENROLLMENT_NOT_FOUND);
    }
}
