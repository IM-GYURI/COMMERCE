package zerobase.common.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zerobase.common.type.Role;

class EnumValidatorTest {

  private EnumValidator validator;

  @BeforeEach
  public void setUp() {
    validator = new EnumValidator();
  }

  /**
   * 특정 Enum 값이 유효한 경우
   */
  @Test
  public void testIsValid_ValidEnumValue() {
    // Given
    ValidEnum annotation = createValidEnumAnnotation(TestEnum.class);
    validator.initialize(annotation);
    Enum<?> value = TestEnum.ONE;

    // When
    boolean isValid = validator.isValid(value, mock(ConstraintValidatorContext.class));

    // Then
    assertTrue(isValid);
  }

  /**
   * 유효하지 않은 Enum 값이 들어올 경우
   */
  @Test
  public void testIsValid_InvalidEnumValue() {
    // Given
    ValidEnum annotation = createValidEnumAnnotation(TestEnum.class);
    validator.initialize(annotation);
    Enum<?> value = Role.CUSTOMER;

    // When
    boolean isValid = validator.isValid(value, mock(ConstraintValidatorContext.class));

    // Then
    assertFalse(isValid);
  }

  /**
   * 테스트를 위해 임시로 생성한 ValidEnum 어노테이션 인스턴스를 반환합니다.
   *
   * @param enumClass
   * @return
   */
  private ValidEnum createValidEnumAnnotation(final Class<? extends Enum<?>> enumClass) {
    return new ValidEnum() {
      @Override
      public Class<? extends java.lang.annotation.Annotation> annotationType() {
        return ValidEnum.class;
      }

      @Override
      public Class<? extends Enum<?>> enumClass() {
        return enumClass;
      }

      @Override
      public String message() {
        return "Invalid Enum";
      }

      @Override
      public Class<?>[] groups() {
        return new Class<?>[0];
      }

      @Override
      public Class<? extends jakarta.validation.Payload>[] payload() {
        return new Class[0];
      }
    };
  }

  private enum TestEnum {
    ONE, TWO, THREE, INVALID
  }
}