package com.wayon.SmartTransfer.exceptions;

public class ResourceCreationException extends Exception {

    public ResourceCreationException() {
        super();
    }

    public ResourceCreationException(String message) {
        super(message);
    }

    public ResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
