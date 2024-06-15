package zerobase.common.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

class CustomAuthenticationEntryPointTest {

  /**
   * 인증 실패 테스트
   *
   * @throws Exception
   */
  @Test
  public void testCommenceAuthenticationFailure() throws Exception {
    // Given
    CustomAuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    AuthenticationException authException = new AuthenticationException("인증 실패") {
    };

    // When
    authenticationEntryPoint.commence(request, response, authException);

    // Then
    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    assertEquals("인증에 실패하였습니다.", response.getErrorMessage());
  }
}