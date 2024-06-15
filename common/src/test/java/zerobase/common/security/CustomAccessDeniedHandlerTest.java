package zerobase.common.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;


class CustomAccessDeniedHandlerTest {

  /**
   * 접근 거부 테스트
   *
   * @throws Exception
   */
  @Test
  public void testHandleAccessDenied() throws Exception {
    // Given
    CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    AccessDeniedException accessDeniedException = new AccessDeniedException("접근 거부");

    // When
    accessDeniedHandler.handle(request, response, accessDeniedException);

    // Then
    assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    assertEquals("접근 권한이 없습니다.", response.getErrorMessage());
  }
}