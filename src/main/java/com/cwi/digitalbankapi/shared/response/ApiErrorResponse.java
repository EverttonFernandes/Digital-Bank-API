package com.cwi.digitalbankapi.shared.response;

public record ApiErrorResponse(
    String key,
    String value
) {
}
