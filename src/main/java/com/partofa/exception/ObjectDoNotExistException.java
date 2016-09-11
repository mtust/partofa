package com.partofa.exception;

public class ObjectDoNotExistException extends GeneralServiceException {
    private int status = 404;
    private String message = "Object do not exists";

    public ObjectDoNotExistException() {
    }

    public ObjectDoNotExistException(String message) {
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
