package com.zerobase.commerce.service;

import static com.zerobase.commerce.exception.ErrorCode.CUSTOMER_NOT_FOUND;

import com.zerobase.commerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring security의 UserDetailsService 구현체
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  /**
   * authenticate 메소드가 실행되면, 해당 메소드를 호출하여 유저 검증 후 인증된 유저 객체를 리턴
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return customerRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(
            CUSTOMER_NOT_FOUND.getMessage()
        ));
  }
}