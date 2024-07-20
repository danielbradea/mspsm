package com.bid90.psm.exceptions;

public class MinioRuntimeException extends RuntimeException {
    public MinioRuntimeException(String message) {
        super(message);
    }

    public MinioRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}