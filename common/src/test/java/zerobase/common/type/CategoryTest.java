package zerobase.common.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import zerobase.common.exception.CommonCustomException;

class CategoryTest {

  /**
   * 유효한 카테고리 키 전달
   */
  @Test
  public void testFromKey_ValidKey() {
    Category category = Category.fromKey("CLOTHES");
    assertEquals(Category.CLOTHES, category);
  }

  /**
   * 유효하지 않은 카테고리 키 전달
   */
  @Test
  public void testFromKey_InvalidKey() {
    String invalidKey = "INVALID_CATEGORY";
    CommonCustomException exception = assertThrows(CommonCustomException.class, () -> {
      Category.fromKey(invalidKey);
    });

    assertEquals("올바르지 않은 요청입니다.", exception.getMessage());
  }
}