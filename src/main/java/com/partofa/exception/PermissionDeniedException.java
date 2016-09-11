package com.partofa.exception;

public class PermissionDeniedException extends GeneralServiceException {

    private int status = 403;
    private String message = "Permission denied";

    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message) {
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
