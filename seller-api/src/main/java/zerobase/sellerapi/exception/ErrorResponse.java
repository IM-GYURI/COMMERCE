package zerobase.sellerapi.exception;

public record ErrorResponse(
    ErrorCode errorCode,
    String message
) {

}
