package com.cwi.digitalbankapi.shared.exception;

public class BusinessException extends RuntimeException {

    private final String key;
    private final String value;

    public BusinessException(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
