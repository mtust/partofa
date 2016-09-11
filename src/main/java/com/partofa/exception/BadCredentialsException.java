package com.partofa.exception;

public class BadCredentialsException extends GeneralServiceException {
    private int status = 401;
    private String message = "Bad login credential";

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
