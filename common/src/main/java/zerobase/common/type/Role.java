package zerobase.common.type;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zerobase.common.exception.CommonCustomException;

/**
 * 사용자의 역할 타입 : Customer/Seller
 */

@Getter
@RequiredArgsConstructor
public enum Role {
  CUSTOMER("ROLE_CUSTOMER"),
  SELLER("ROLE_SELLER");

  private final String key;

  public static Role fromKey(String key) {
    return Arrays.stream(values())
        .filter(o -> o.getKey().equals(key))
        .findFirst()
        .orElseThrow(() -> new CommonCustomException(INVALID_REQUEST,
            INVALID_REQUEST.getMessage()));
  }
}
