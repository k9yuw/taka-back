package taka.takaspring.Rental.exception;

import taka.takaspring.common.exception.ErrorCode;
import taka.takaspring.common.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String message){
        super(message, ErrorCode.ITEM_NOT_FOUND);
    }
}

