package com.nvc.scanbar.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScanbarException extends RuntimeException{
    private String traceIdentifier;
    public ScanbarException(String message, String traceIdentifier) {
        super(message);
        this.traceIdentifier = traceIdentifier;
    }

    public ScanbarException(String traceIdentifier) {
        this.traceIdentifier = traceIdentifier;
    }
}
