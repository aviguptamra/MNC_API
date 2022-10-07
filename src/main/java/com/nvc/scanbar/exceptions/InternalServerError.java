package com.nvc.scanbar.exceptions;

public class InternalServerError extends ScanbarException{
    public InternalServerError(String message, String traceIdentifier) {
        super(message, traceIdentifier);
    }
    public InternalServerError(String traceIdentifier) {
        super(traceIdentifier);
    }
}
