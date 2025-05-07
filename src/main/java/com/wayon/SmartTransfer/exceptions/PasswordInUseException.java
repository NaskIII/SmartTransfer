package com.wayon.SmartTransfer.exceptions;

public class PasswordInUseException extends Exception {

    public PasswordInUseException() { super(); }

    public PasswordInUseException(String errorMessage) { super(errorMessage); }

    public PasswordInUseException(String errorMessage, Throwable error){ super(errorMessage, error); }
}
