package com.example.insiderback.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<T> extends CommonResponse{

    private List<T> list;

    public ListResponse(List<T> list) {
        this.list = list;
    }

    public ListResponse(List<T> list, String msg) {
        super(msg);
        this.list = list;
    }

    public ListResponse(List<T> list, String message, boolean isSuccess) {
        super(message, isSuccess);
        this.list = list;
    }
}