package com.partofa.exception;

public class ObjectAlreadyExistException extends GeneralServiceException {
    private int status = 409;
    private String message = "Object already exists";

    public ObjectAlreadyExistException() {
    }

    public ObjectAlreadyExistException(String message) {
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
