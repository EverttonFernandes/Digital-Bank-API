package com.digitalbankapi.shared.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final String key;
    private final String value;
    private final HttpStatus httpStatus;

    public BusinessException(String key, String value, HttpStatus httpStatus) {
        super(value);
        this.key = key;
        this.value = value;
        this.httpStatus = httpStatus;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
