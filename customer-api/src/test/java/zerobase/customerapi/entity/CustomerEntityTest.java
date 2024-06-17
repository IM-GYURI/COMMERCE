package zerobase.customerapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.customerapi.dto.customer.CustomerEditDto;

@ExtendWith(MockitoExtension.class)
public class CustomerEntityTest {

  /**
   * 고객 정보 수정 테스트
   */
  @Test
  void testUpdateCustomer() {
    // Given
    CustomerEntity customerEntity = CustomerEntity.builder()
        .customerKey("C12345")
        .email("test@example.com")
        .password("password12345!")
        .name("Test User")
        .phone("010-1234-5678")
        .address("Test Address")
        .point(100L)
        .birth(LocalDate.parse("2000-01-01"))
        .build();

    CustomerEditDto customerEditDto = CustomerEditDto.builder()
        .name("Updated Name")
        .phone("010-9876-5432")
        .address("Updated Address")
        .birth(LocalDate.parse("1990-01-01"))
        .build();

    // When
    customerEntity.updateCustomer(customerEditDto);

    // Then
    assertEquals("Updated Name", customerEntity.getName());
    assertEquals("010-9876-5432", customerEntity.getPhone());
    assertEquals("Updated Address", customerEntity.getAddress());
    assertEquals(LocalDate.parse("1990-01-01"), customerEntity.getBirth());
  }

  /**
   * 포인트 충전 테스트
   */
  @Test
  void testPlusPoint() {
    // Given
    CustomerEntity customerEntity = CustomerEntity.builder()
        .customerKey("C12345")
        .email("test@example.com")
        .password("password12345!")
        .name("Test User")
        .phone("010-1234-5678")
        .address("Test Address")
        .point(100L)
        .birth(LocalDate.parse("2000-01-01"))
        .build();

    // When
    customerEntity.plusPoint(50L);

    // Then
    assertEquals(150L, customerEntity.getPoint());
  }

  /**
   * 포인트를 통한 결제 테스트
   */
  @Test
  void testMinusPoint() {
    // Given
    CustomerEntity customerEntity = CustomerEntity.builder()
        .customerKey("C12345")
        .email("test@example.com")
        .password("password12345!")
        .name("Test User")
        .phone("010-1234-5678")
        .address("Test Address")
        .point(100L)
        .birth(LocalDate.parse("2000-01-01"))
        .build();

    // When
    customerEntity.minusPoint(30L);

    // Then
    assertEquals(70L, customerEntity.getPoint());
  }
}