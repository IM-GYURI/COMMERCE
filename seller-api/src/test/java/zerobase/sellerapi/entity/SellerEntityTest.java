package zerobase.sellerapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import zerobase.common.type.Role;
import zerobase.sellerapi.dto.SellerEditDto;

public class SellerEntityTest {

  /**
   * 판매자 권한 테스트
   */
  @Test
  void getAuthorities_ReturnsCorrectAuthority() {
    // Given
    SellerEntity sellerEntity = SellerEntity.builder()
        .sellerKey("S12345")
        .email("test1234@example.com")
        .password("password1234!")
        .name("Test Seller")
        .phone("010-1234-5678")
        .address("address 12345")
        .build();

    // When
    Collection<? extends SimpleGrantedAuthority> authorities
        = (Collection<? extends SimpleGrantedAuthority>) sellerEntity.getAuthorities();

    // Then
    assertEquals(1, authorities.size());
    assertEquals(Role.SELLER.getKey(), authorities.iterator().next().getAuthority());
  }

  /**
   * 판매자 정보 수정 테스트
   */
  @Test
  void updateSeller_UpdatesFieldsCorrectly() {
    // Given
    SellerEntity sellerEntity = SellerEntity.builder()
        .sellerKey("S12345")
        .email("test1234@example.com")
        .password("password1234!")
        .name("Test Seller")
        .phone("010-1234-5678")
        .address("address 12345")
        .build();

    SellerEditDto sellerEditDto = mock(SellerEditDto.class);
    String newName = "New Name";
    String newPhone = "010-9876-5432";
    String newAddress = "New Address";

    when(sellerEditDto.getName()).thenReturn(newName);
    when(sellerEditDto.getPhone()).thenReturn(newPhone);
    when(sellerEditDto.getAddress()).thenReturn(newAddress);

    // When
    sellerEntity.updateSeller(sellerEditDto);

    // Then
    assertEquals(newName, sellerEntity.getName());
    assertEquals(newPhone, sellerEntity.getPhone());
    assertEquals(newAddress, sellerEntity.getAddress());
  }
}
