package zerobase.customerapi.service;

import static zerobase.customerapi.exception.ErrorCode.CUSTOMER_ALREADY_EXISTS;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.customerapi.dto.customer.CustomerDto;
import zerobase.customerapi.dto.customer.CustomerSignInDto;
import zerobase.customerapi.dto.customer.CustomerSignUpDto;
import zerobase.customerapi.entity.CustomerEntity;
import zerobase.customerapi.exception.CustomException;
import zerobase.customerapi.repository.CustomerRepository;
import zerobase.customerapi.util.KeyGenerator;

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