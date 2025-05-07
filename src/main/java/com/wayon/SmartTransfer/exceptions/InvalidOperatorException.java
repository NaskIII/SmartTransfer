package com.wayon.SmartTransfer.exceptions;

public class InvalidOperatorException extends Exception {

    public InvalidOperatorException() { super(); }

    public InvalidOperatorException(String errorMessage) { super(errorMessage); }

    public InvalidOperatorException(String errorMessage, Throwable error) { super(errorMessage, error); }
}
