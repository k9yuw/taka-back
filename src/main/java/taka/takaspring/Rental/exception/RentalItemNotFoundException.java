package taka.takaspring.Rental.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class RentalItemNotFoundException extends NotFoundException {
    public RentalItemNotFoundException(String message){
        super(message, ErrorCode.ITEM_NOT_FOUND);
    }
}
