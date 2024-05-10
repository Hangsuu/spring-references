package com.references.common.responseModel.model;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class CmmnResponseModel<T> {

    private boolean isSuccess;
    private String message;
    private T data;

    private Map<String, Object> extraData;
    private String extraString;
    private String returnCode;

    public CmmnResponseModel() {
        this.isSuccess = true;
        this.message = null;
        this.data = null;
        this.extraData = new HashMap<>();
    }

    public CmmnResponseModel(T data) {
        this.data = data;
        this.isSuccess = true;
        this.message = null;
        this.extraData = new HashMap<>();
    }

    public CmmnResponseModel(String msg) {
        this.isSuccess = true;
        this.message = msg;
        this.data = null;
        this.extraData = new HashMap<>();
    }

    public void put(String key, Object value) {
        extraData.put(key, value);
    }

    public Object get(String key) {
        return extraData.get(key);
    }

}
