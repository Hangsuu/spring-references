package com.example.insiderback.common.exception;

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
        if(originalException != null && originalException instanceof CommonException) {
            CommonException commonException = (CommonException) originalException;
            log.error("handleCustomException throw CommonException : {}", commonException.getErrorCode());
            log.error("handleCustomException throw CommonException : {}",
                    commonException.getErrorCode().getHttpStatus());
            log.error("handleCustomException throw CommonException : {}",
                    commonException.getErrorCode().getDetail());

            e.printStackTrace();
            return ErrorResponse.toResponseEntity(commonException.getErrorCode().getHttpStatus(),
                    commonException.getErrorCode().getDetail());
        } else {
            log.error("handle Exception throw {} : ", e);
            e.printStackTrace();
            String errorMessage = (originalException != null) ? originalException.toString() : "Unknown cause";
            return ErrorResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }
    }
}