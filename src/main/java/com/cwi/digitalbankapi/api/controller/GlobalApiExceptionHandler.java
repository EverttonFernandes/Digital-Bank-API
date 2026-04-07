package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.shared.exception.BusinessException;
import com.cwi.digitalbankapi.shared.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException businessException) {
        return ResponseEntity.status(businessException.getHttpStatus())
            .body(new ApiErrorResponse(
                businessException.getKey(),
                businessException.getValue()
            ));
    }
}
