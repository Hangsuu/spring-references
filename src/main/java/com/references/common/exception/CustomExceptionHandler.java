package com.references.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ Exception.class})
    protected ResponseEntity handleException(Exception e) {
        // 발생한 Exception 클래스 추출
        Throwable originalException = e.getCause();
        if(e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            log.error("handleCustomException : ");
            e.printStackTrace();
            return ErrorResponse.toResponseEntity(commonException.getStatus(),
                    commonException.getMessage());
        } else if(originalException != null && originalException instanceof CommonException) {
            CommonException commonException = (CommonException) originalException;
            log.error("handleCustomException throw CommonException : {}", commonException.getErrorCode());
            log.error("handleCustomException throw CommonException : {}",
                    commonException.getErrorCode().getHttpStatus());
            log.error("handleCustomException throw CommonException : {}",
                    commonException.getErrorCode().getDetail());

            e.printStackTrace();
            return ErrorResponse.toResponseEntity(commonException.getStatus(),
                    commonException.getMessage());
        } else {
            log.error("handleRuntimeException");
            e.printStackTrace();
            String errorMessage = (e.getMessage() != null) ? e.toString() : e + ": Unknown cause";
            return ErrorResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }
    }
}