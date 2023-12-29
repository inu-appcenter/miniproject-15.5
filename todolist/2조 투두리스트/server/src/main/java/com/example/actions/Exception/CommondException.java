package com.example.actions.Exception;

import lombok.Getter;

@Getter
public class CommondException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CommondException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
