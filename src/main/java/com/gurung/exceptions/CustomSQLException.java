package com.gurung.exceptions;


public class CustomSQLException extends Exception {
    public CustomSQLException(String message) {
        super(message);
    }

    public CustomSQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
