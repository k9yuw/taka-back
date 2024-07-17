package taka.takaspring.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import taka.takaspring.common.Constants;

public class CustomException extends Exception{

    @Getter
    private Constants.ExceptionClass exceptionClass;
    @Getter
    private HttpStatus httpStatus;

    public CustomException(Constants.ExceptionClass exceptionClass, HttpStatus httpStatus, String message){
        super(exceptionClass.toString() + message);
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public String getHttpStatusType(){
        return httpStatus.getReasonPhrase();
    }

    public int getHttpStatusCode(){
        return httpStatus.value();
    }

}
