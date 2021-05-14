package com.tms.speeding.util;

public class ResponseObject {
    private boolean success;
    private String type;
    private Object data;
    private String message;

    public ResponseObject() {
        this.success = true;
        this.type = "success";
        this.message = "Ok";
    }

    public ResponseObject(Object data) {
        this();
        this.data = data;
    }

    public ResponseObject(boolean success, String type, Object data, String message) {
        this.success = success;
        this.type = type;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
