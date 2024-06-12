package zerobase.customerapi.service;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;
import static zerobase.customerapi.exception.CustomerErrorCode.CUSTOMER_ALREADY_EXISTS;
import static zerobase.customerapi.exception.CustomerErrorCode.CUSTOMER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.util.KeyGenerator;
import zerobase.customerapi.dto.customer.CustomerDto;
import zerobase.customerapi.dto.customer.CustomerEditDto;
import zerobase.customerapi.dto.customer.CustomerPointDto;
import zerobase.customerapi.dto.customer.CustomerSignInDto;
import zerobase.customerapi.dto.customer.CustomerSignUpDto;
import zerobase.customerapi.entity.CustomerEntity;
import zerobase.customerapi.repository.CustomerRepository;


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
    validateCustomerExistsByEmail(signUpDto.getEmail());

    CustomerEntity savedCustomer = customerRepository.save(
        signUpDto.toEntity(
            keyGenerator.generateKey(),
            passwordEncoder.encode(signUpDto.getPassword())
        )
    );

    return CustomerDto.fromEntity(savedCustomer);
  }

  private void validateCustomerExistsByEmail(String email) {
    if (customerRepository.existsByEmail(email)) {
      throw new CommonCustomException(CUSTOMER_ALREADY_EXISTS);
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

  public CustomerDto findByEmail(String email) {
    return CustomerDto.fromEntity(customerRepository.findByEmail(email)
        .orElseThrow(() -> new CommonCustomException(CUSTOMER_NOT_FOUND)));
  }

  @Transactional
  public CustomerDto edit(CustomerEditDto customerEditDto) {
    CustomerEntity customer = customerRepository.findByCustomerKey(customerEditDto.getCustomerKey())
        .orElseThrow(() -> new CommonCustomException(CUSTOMER_NOT_FOUND));

    customer.updateCustomer(customerEditDto);

    return CustomerDto.fromEntity(customer);
  }

  @Transactional
  public String delete(String customerKey) {
    validateCustomerExistsByCustomerKey(customerKey);

    customerRepository.deleteByCustomerKey(customerKey);

    return customerKey;
  }

  private void validateCustomerExistsByCustomerKey(String customerKey) {
    if (!customerRepository.existsByCustomerKey(customerKey)) {
      throw new CommonCustomException(CUSTOMER_NOT_FOUND);
    }
  }

  public CustomerDto validateAuthorizationAndGetCustomer(String customerKey, String email) {
    CustomerDto customerDto = findByEmail(email);
    String keyOfCustomer = customerDto.getCustomerKey();

    if (!customerKey.equals(keyOfCustomer)) {
      throw new CommonCustomException(INVALID_REQUEST);
    }

    return customerDto;
  }

  @Transactional
  public CustomerPointDto rechargePoint(String customerKey, Long point) {
    CustomerEntity customer = customerRepository.findByCustomerKey(customerKey)
        .orElseThrow(() -> new CommonCustomException(CUSTOMER_NOT_FOUND));

    customer.updatePoint(point);

    return CustomerPointDto.builder()
        .customerKey(customerKey)
        .point(customer.getPoint())
        .build();
  }
}
