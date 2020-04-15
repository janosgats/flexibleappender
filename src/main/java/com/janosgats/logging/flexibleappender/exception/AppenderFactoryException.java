package com.janosgats.logging.flexibleappender.exception;

public class AppenderFactoryException extends RuntimeException {
    public AppenderFactoryException() {
    }

    public AppenderFactoryException(Throwable e) {
        super(e);
    }

    public AppenderFactoryException(String message) {
        super(message);
    }

    public AppenderFactoryException(String message, Throwable e) {
        super(message, e);
    }
}
