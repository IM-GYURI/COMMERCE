package zerobase.customerapi.exception;

public record ErrorResponse(
    ErrorCode errorCode,
    String message
) {

}
