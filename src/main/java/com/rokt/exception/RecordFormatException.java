package com.rokt.exception;

public class RecordFormatException extends FileSearchRuntimeException {
    public RecordFormatException() {
    }

    public RecordFormatException(String message) {
        super(message);
    }

    public RecordFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordFormatException(Throwable cause) {
        super(cause);
    }

    public RecordFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
