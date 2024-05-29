package com.zerobase.commerce.service;

import static com.zerobase.commerce.exception.ErrorCode.CUSTOMER_ALREADY_EXISTS;

import com.zerobase.commerce.dto.customer.CustomerDto;
import com.zerobase.commerce.dto.customer.CustomerSignInDto;
import com.zerobase.commerce.dto.customer.CustomerSignUpDto;
import com.zerobase.commerce.entity.CustomerEntity;
import com.zerobase.commerce.exception.CustomException;
import com.zerobase.commerce.repository.CustomerRepository;
import com.zerobase.commerce.util.KeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final KeyGenerator keyGenerator;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  /**
   * 회원가입 : 이메일을 통해 이미 등록된 고객인지 여부를 확인한 후 진행
   */
  @Transactional
  public CustomerDto signUp(CustomerSignUpDto signUpDto) {
    validateCustomerExists(signUpDto.getEmail());

    CustomerEntity savedCustomer = customerRepository.save(
        signUpDto.toEntity(
            keyGenerator.generateKey(),
            passwordEncoder.encode(signUpDto.getPassword())
        )
    );

    return CustomerDto.fromEntity(savedCustomer);
  }

  private void validateCustomerExists(String email) {
    if (customerRepository.existsByEmail(email)) {
      throw new CustomException(CUSTOMER_ALREADY_EXISTS);
    }
  }

  /**
   * 로그인 : 로그인 요청 정보를 spring security의 authenticate 메소드를 사용하여 검증
   */
  public CustomerDto signIn(CustomerSignInDto signInDto) {
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(
            signInDto.email(), signInDto.password()
        ));

    return CustomerDto.fromEntity((CustomerEntity) authentication.getPrincipal());
  }
}
