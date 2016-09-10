package com.partofa.dto;


public class RestMessageDTO {
    private String message;
    private boolean success;


    public RestMessageDTO(final String message, final boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static RestMessageDTO createSuccessRestMessageDTO(String message) {
        return new RestMessageDTO(message, true);
    }

    public static RestMessageDTO createFailureRestMessageDTO(String message) {
        return new RestMessageDTO(message, false);
    }

    public RestMessageDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestMessageDTO that = (RestMessageDTO) o;

        if (success != that.success) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (success ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RestMessageDTO{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
