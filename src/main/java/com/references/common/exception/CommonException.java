package com.references.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException{
    private ErrorCode errorCode;
    private HttpStatus status;
    private String message;
    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
    public CommonException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.status = errorCode.getHttpStatus();
        this.message = errorCode.getDetail();
    }
}
