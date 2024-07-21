package taka.takaspring.Rental.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class RentalRecordNotFoundException extends NotFoundException {
    public RentalRecordNotFoundException(String message){
        super(message, ErrorCode.RENTAL_RECORD_NOT_FOUND);
    }
}
