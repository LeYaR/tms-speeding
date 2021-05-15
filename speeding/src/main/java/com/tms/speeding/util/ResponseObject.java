package com.tms.speeding.util;

public class ResponseObject {
    private boolean success;
    private String type;
    private String message;

    public ResponseObject() {
        this.success = true;
        this.type = "success";
        this.message = "Ok";
    }

    public ResponseObject(String message) {
        this.message = message;
    }

    public ResponseObject(String type, String message) {
        this(message);
        this.type = type;
    }

    public ResponseObject(boolean success, String type, String message) {
        this(type, message);
        this.success = success;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
