package zerobase.customerapi.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.customerapi.security.CustomerTokenProvider;
import zerobase.customerapi.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomerSecurityTestConfig.class)
class CustomerSecurityConfigTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CustomerTokenProvider tokenProvider;

  @Autowired
  private CustomerService customerService;


  /**
   * 누구나 접근 가능
   *
   * @throws Exception
   */
  @Test
  void whenAccessPublicEndpoint_thenReturnOk() throws Exception {
    mockMvc.perform(get("/product/name"))
        .andExpect(status().isOk());
  }

  /**
   * 권한이 있어야만 접근 가능 - 실패
   *
   * @throws Exception
   */
  @Test
  void whenAccessProtectedEndpointWithoutAuth_thenReturnUnauthorized() throws Exception {
    mockMvc.perform(get("/customer/C12345"))
        .andExpect(status().isUnauthorized());
  }
}