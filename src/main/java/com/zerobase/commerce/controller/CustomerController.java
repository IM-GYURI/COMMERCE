package com.zerobase.commerce.controller;

import com.zerobase.commerce.dto.customer.CustomerDto;
import com.zerobase.commerce.dto.customer.CustomerSignInDto;
import com.zerobase.commerce.dto.customer.CustomerSignUpDto;
import com.zerobase.commerce.security.TokenProvider;
import com.zerobase.commerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 없이 접근 가능
 */
@RequestMapping("/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

  private final CustomerService customerService;
  private final TokenProvider tokenProvider;

  /**
   * 회원 가입
   */
  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody @Valid CustomerSignUpDto signUpDto) {
    return ResponseEntity.ok(customerService.signUp(signUpDto));
  }

  /**
   * 로그인
   */
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody @Valid CustomerSignInDto signInDto) {
    CustomerDto customerDto = customerService.signIn(signInDto);
    String token = tokenProvider.generateToken(customerDto);

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, token);

    return ResponseEntity.ok().headers(headers).body(customerDto);
  }
}
