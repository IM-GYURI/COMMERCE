package zerobase.customerapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zerobase.common.security.CustomAccessDeniedHandler;
import zerobase.common.security.CustomAuthenticationEntryPoint;
import zerobase.customerapi.security.CustomerAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CustomerSecurityConfig {

  private final CustomerAuthenticationFilter customerAuthenticationFilter;

  @Bean(name = "customerSecurityFilterChain")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .headers(c -> c.frameOptions(
            HeadersConfigurer.FrameOptionsConfig::disable).disable())
        .sessionManagement(c ->
            c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((request) -> request
            .requestMatchers(
                "/",
                "/error",
                "/swagger-ui",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                "/customer/signup",
                "/customer/signin",
                "/product/**"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(customerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .accessDeniedHandler(new CustomAccessDeniedHandler()));

    return http.build();
  }

  @Bean(name = "customerPasswordEncoder")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}