package zerobase.common.exception;

public record ErrorResponse(
    ErrorCode errorCode,
    String message
) {

}
