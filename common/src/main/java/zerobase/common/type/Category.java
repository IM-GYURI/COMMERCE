package zerobase.common.type;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zerobase.common.exception.CommonCustomException;

@Getter
@RequiredArgsConstructor
public enum Category {
  BOOK("BOOK"),
  CLOTHES("CLOTHES"),
  COSMETIC("COSMETIC"),
  DIGITAL("DIGITAL"),
  FOOD("FOOD"),
  FURNITURE("FURNITURE"),
  HOUSEHOLD_GOODS("HOUSEHOLD_GOODS"),
  KITCHEN_UTENSILS("KITCHEN_UTENSILS"),
  PET_GOODS("PET_GOODS"),
  SPORTS("SPORTS");

  private final String key;

  public static Category fromKey(String key) {
    return Arrays.stream(values())
        .filter(o -> o.getKey().equals(key))
        .findFirst()
        .orElseThrow(() -> new CommonCustomException(INVALID_REQUEST,
            INVALID_REQUEST.getMessage()));
  }
}
