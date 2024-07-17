package taka.takaspring.Member.exception;

import taka.takaspring.common.exception.DuplicateException;
import taka.takaspring.common.exception.ErrorCode;

public class StudentNumberDuplicateException extends DuplicateException {

    public StudentNumberDuplicateException(String message) {
        super(message, ErrorCode.STUDENT_NUMBER_DUPLICATE);
    }

}
