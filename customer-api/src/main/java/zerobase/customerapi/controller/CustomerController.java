package zerobase.customerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import zerobase.customerapi.dto.customer.CustomerEditDto;
import zerobase.customerapi.dto.customer.CustomerPointDto;
import zerobase.customerapi.dto.customer.CustomerSignInDto;
import zerobase.customerapi.dto.customer.CustomerSignUpDto;
import zerobase.customerapi.security.CustomerTokenProvider;
import zerobase.customerapi.service.CustomerService;

/**
 * 인증 없이 접근 가능
 */
@RequestMapping("/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

  private final CustomerService customerService;
  private final CustomerTokenProvider customerTokenProvider;

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
    String token = customerTokenProvider.generateToken(customerDto);

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
  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping("/{customerKey}")
  public ResponseEntity<?> customerInformation(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    return ResponseEntity.ok(customerService.validateAuthorizationAndGetCustomer(customerKey,
        email));
  }

  /**
   * 회원 정보 수정 : 본인만 가능
   *
   * @param customerKey
   * @param token
   * @param customerEditDto
   * @return
   */
  @PreAuthorize("hasRole('CUSTOMER')")
  @PatchMapping("/{customerKey}")
  public ResponseEntity<?> editCustomerInformation(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token, @RequestBody CustomerEditDto customerEditDto) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    return ResponseEntity.ok(customerService.edit(customerEditDto));
  }

  /**
   * 회원 탈퇴 : 본인만 가능
   *
   * @param customerKey
   * @param token
   * @return
   */
  @PreAuthorize("hasRole('CUSTOMER')")
  @DeleteMapping("/{customerKey}")
  public ResponseEntity<?> deleteCustomer(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);
    
    return ResponseEntity.ok("delete " + customerService.delete(customerKey));
  }

  /**
   * 포인트 충전
   *
   * @param customerKey
   * @param token
   * @param customerPointDto
   * @return
   */
  @PreAuthorize("hasRole('CUSTOMER')")
  @PatchMapping("/point/{customerKey}")
  public ResponseEntity<?> rechargePoint(@PathVariable String customerKey,
      @RequestHeader("Authorization") String token,
      @RequestBody CustomerPointDto customerPointDto) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Authentication authentication = customerTokenProvider.getAuthentication(token);
    String email = authentication.getName();

    CustomerDto customerDto = customerService.validateAuthorizationAndGetCustomer(customerKey,
        email);

    return ResponseEntity.ok(
        customerService.chargePoint(customerKey, customerPointDto.getPoint()));
  }
}
