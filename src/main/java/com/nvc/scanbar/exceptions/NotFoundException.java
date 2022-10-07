package com.nvc.scanbar.exceptions;

public class NotFoundException extends ScanbarException{
    public NotFoundException(String message, String traceIdentifier) {
        super(message, traceIdentifier);
    }
}
