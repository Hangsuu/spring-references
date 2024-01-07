package com.example.insiderback.common.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleResponse <T> extends CommonResponse {
    private T data;

    public SingleResponse(T data) {
        this.data = data;
    }

    public SingleResponse(T data, String msg) {
        super(msg);
        this.data = data;
    }

    public SingleResponse(T data, String message, boolean isSuccess) {
        super(message, isSuccess);
        this.data = data;
    }
}