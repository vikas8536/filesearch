package com.rokt.exception;

public class FileSearchRuntimeException extends RuntimeException{
    public FileSearchRuntimeException() {
    }

    public FileSearchRuntimeException(String message) {
        super(message);
    }

    public FileSearchRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSearchRuntimeException(Throwable cause) {
        super(cause);
    }

    public FileSearchRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
