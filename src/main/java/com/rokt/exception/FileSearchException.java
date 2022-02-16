package com.rokt.exception;

public class FileSearchException extends Exception{
    public FileSearchException() {
        super();
    }

    public FileSearchException(String message) {
        super(message);
    }

    public FileSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSearchException(Throwable cause) {
        super(cause);
    }

    protected FileSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
