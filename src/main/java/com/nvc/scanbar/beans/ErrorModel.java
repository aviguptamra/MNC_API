package com.nvc.scanbar.beans;

import com.nvc.scanbar.exceptions.ScanbarException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {
    private HttpStatus httpStatus;
    private Date timestamp = new Date();
    private String message;
    private String traceIdentifier;


    public ErrorModel(HttpStatus status, ScanbarException e) {
        httpStatus = status;
        this.message = e.getMessage();
        this.traceIdentifier = e.getTraceIdentifier();
    }

    public ErrorModel(HttpStatus status, String message, ScanbarException e) {
        httpStatus = status;
        this.message = message;
        this.traceIdentifier = e.getTraceIdentifier();
    }
}
