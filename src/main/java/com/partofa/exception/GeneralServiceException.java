package com.partofa.exception;

public class GeneralServiceException extends RuntimeException {

    private int status = 500;
    private final String message = "General message";

    public GeneralServiceException() {
        super();
    }

    public GeneralServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralServiceException(String message) {
        super(message);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
