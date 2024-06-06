package zerobase.common.exception;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {

  String getMessage();

  HttpStatusCode getStatus();
}