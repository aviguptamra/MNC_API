package com.nvc.scanbar.exceptions;
public class DataConflictException extends ScanbarException{
    public DataConflictException(String message, String traceIdentifier) {
        super(message, traceIdentifier);
    }
}
