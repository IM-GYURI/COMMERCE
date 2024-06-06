package zerobase.customerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.customerapi.dto.customer.CustomerDto;
import zerobase.customerapi.dto.customer.CustomerSignInDto;
import zerobase.customerapi.dto.customer.CustomerSignUpDto;
import zerobase.customerapi.dto.customer.EditDto;
import zerobase.customerapi.repository.CustomerRepository;
import zerobase.customerapi.security.TokenProvider;
import zerobase.customerapi.service.CustomerService;

/**
 * 인증 없이 접근 가능
 */
@RequestMapping("/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

  private final CustomerService customerService;
  private final CustomerRepository customerRepository;
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

  /**
   * 회원 정보 조회 : 본인만 가능
   *
   * @param customerKey
   * @param token
   * @return
   */
  @GetMapping("/{customerKey}")
  public ResponseEntity<?> customerInformation(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      return ResponseEntity.status(403).body("Access Denied");
    }

    if (!tokenProvider.validateToken(token)) {
      return ResponseEntity.status(403).body("Invalid Token");
    }

    Authentication authentication = tokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.findByEmail(email);
    String keyOfCustomer = customerDto.getCustomerKey();

    if (!customerKey.equals(keyOfCustomer)) {
      return ResponseEntity.status(403).body("Access Denied");
    }

    return ResponseEntity.ok(customerDto);
  }

  /**
   * 회원 정보 수정 : 본인만 가능
   *
   * @param customerKey
   * @param token
   * @param editDto
   * @return
   */
  @PatchMapping("/{customerKey}")
  public ResponseEntity<?> editCustomerInformation(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token, @RequestBody EditDto editDto) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      return ResponseEntity.status(403).body("Access Denied");
    }

    if (!tokenProvider.validateToken(token)) {
      return ResponseEntity.status(403).body("Invalid Token");
    }

    Authentication authentication = tokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.findByEmail(email);
    String keyOfCustomer = customerDto.getCustomerKey();

    if (!customerKey.equals(keyOfCustomer)) {
      return ResponseEntity.status(403).body("Access Denied");
    }

    CustomerDto updated = customerService.edit(editDto);

    return ResponseEntity.ok(updated);
  }

  /**
   * 회원 탈퇴 : 본인만 가능
   *
   * @param customerKey
   * @param token
   * @return
   */
  @DeleteMapping("/{customerKey}")
  public ResponseEntity<?> deleteCustomer(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      return ResponseEntity.status(403).body("Access Denied");
    }

    if (!tokenProvider.validateToken(token)) {
      return ResponseEntity.status(403).body("Invalid Token");
    }

    Authentication authentication = tokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.findByEmail(email);
    String keyOfCustomer = customerDto.getCustomerKey();

    if (!customerKey.equals(keyOfCustomer)) {
      return ResponseEntity.status(403).body("Access Denied");
    }

    customerKey = customerService.delete(customerKey);

    return ResponseEntity.ok("delete " + customerKey);
  }
}
