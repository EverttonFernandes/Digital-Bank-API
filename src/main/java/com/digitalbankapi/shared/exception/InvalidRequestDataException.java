package com.digitalbankapi.shared.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestDataException extends BusinessException {

    public InvalidRequestDataException(String value) {
        super(
                "INVALID_REQUEST_DATA",
                value,
                HttpStatus.BAD_REQUEST
        );
    }
}
