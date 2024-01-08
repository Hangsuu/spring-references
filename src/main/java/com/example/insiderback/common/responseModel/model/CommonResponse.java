package com.example.insiderback.common.responseModel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    private boolean isSuccess;

    private int code;

    private String message;

    public CommonResponse(){}
    public CommonResponse(String msg){
        this.message = msg;
        this.isSuccess = true;
    }

    public CommonResponse(String message, boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
