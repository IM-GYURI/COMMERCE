package com.zerobase.commerce.exception;

public record ErrorResponse(
    ErrorCode errorCode,
    String message
) {

}
