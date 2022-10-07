package com.nvc.scanbar.exceptions;

public class ValidationException extends ScanbarException{
    public ValidationException(String message, String traceIdentifier) {
        super(message, traceIdentifier);
    }
}
