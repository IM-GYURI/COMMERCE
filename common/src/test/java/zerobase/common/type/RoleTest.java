package zerobase.common.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import zerobase.common.exception.CommonCustomException;

class RoleTest {

  /**
   * 유효한 역할 키 전달 : CUSTOMER
   */
  @Test
  public void testFromKey_ValidKey_Customer() {
    Role role = Role.fromKey("ROLE_CUSTOMER");
    assertEquals(Role.CUSTOMER, role);
  }

  /**
   * 유효한 역할 키 전달 : SELLER
   */
  @Test
  public void testFromKey_ValidKey_Seller() {
    Role role = Role.fromKey("ROLE_SELLER");
    assertEquals(Role.SELLER, role);
  }

  /**
   * 유효하지 않은 역할 키 전달
   */
  @Test
  public void testFromKey_InvalidKey() {
    String invalidKey = "INVALID_ROLE";
    CommonCustomException exception = assertThrows(CommonCustomException.class, () -> {
      Role.fromKey(invalidKey);
    });

    assertEquals("올바르지 않은 요청입니다.", exception.getMessage());
  }
}