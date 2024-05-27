package com.zerobase.commerce.type;

import static com.zerobase.commerce.exception.ErrorCode.INVALID_REQUEST;

import com.zerobase.commerce.exception.CustomException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
        .orElseThrow(() -> new CustomException(INVALID_REQUEST,
            INVALID_REQUEST.getMessage()));
  }
}
