package taka.takaspring.Rental.exception;

import taka.takaspring.common.exception.CustomException;
import taka.takaspring.common.exception.ErrorCode;

public class ItemAlreadyRentException extends CustomException {
    public ItemAlreadyRentException(String message){
        super(message, ErrorCode.ITEM_ALREADY_RENT);
    }
}
