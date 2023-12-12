package com.ancevt.command;

public class ReplException extends RuntimeException {
    public ReplException() {
    }

    public ReplException(String message) {
        super(message);
    }

    public ReplException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplException(Throwable cause) {
        super(cause);
    }

    public ReplException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
