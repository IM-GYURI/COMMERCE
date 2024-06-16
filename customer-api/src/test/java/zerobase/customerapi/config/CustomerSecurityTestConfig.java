package zerobase.customerapi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zerobase.customerapi.security.CustomerTokenProvider;

@TestConfiguration
@Import(CustomerSecurityConfig.class)
public class CustomerSecurityTestConfig {

  @Bean
  public CustomerTokenProvider customerTokenProvider() {
    return new CustomerTokenProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}