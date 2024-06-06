package zerobase.sellerapi.controller;

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
import zerobase.sellerapi.dto.seller.EditDto;
import zerobase.sellerapi.dto.seller.SellerDto;
import zerobase.sellerapi.dto.seller.SellerSignInDto;
import zerobase.sellerapi.dto.seller.SellerSignUpDto;
import zerobase.sellerapi.security.TokenProvider;
import zerobase.sellerapi.service.SellerService;

@RequestMapping("/seller")
@RequiredArgsConstructor
@RestController
public class SellerController {

  private final SellerService sellerService;
  private final TokenProvider tokenProvider;

  /**
   * 회원 가입
   */
  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody @Valid SellerSignUpDto signUpDto) {
    return ResponseEntity.ok(sellerService.signUp(signUpDto));
  }

  /**
   * 로그인
   */
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody @Valid SellerSignInDto signInDto) {
    SellerDto sellerDto = sellerService.signIn(signInDto);
    String token = tokenProvider.generateTokenBySeller(sellerDto);

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, token);

    return ResponseEntity.ok().headers(headers).body(sellerDto);
  }

  /**
   * 회원 정보 조회 : 본인만 가능
   *
   * @param sellerKey
   * @param token
   * @return
   */
  @GetMapping("/{sellerKey}")
  public ResponseEntity<?> customerInformation(@PathVariable String sellerKey,
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

    SellerDto sellerDto = sellerService.findByEmail(email);
    String keyOfSeller = sellerDto.getSellerKey();

    if (!sellerKey.equals(keyOfSeller)) {
      return ResponseEntity.status(403).body("Access Denied");
    }

    return ResponseEntity.ok(sellerDto);
  }

  /**
   * 회원 정보 수정 : 본인만 가능
   *
   * @param sellerKey
   * @param token
   * @param editDto
   * @return
   */
  @PatchMapping("/{sellerKey}")
  public ResponseEntity<?> editCustomerInformation(@PathVariable String sellerKey,
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

    SellerDto sellerDto = sellerService.findByEmail(email);
    String keyOfSeller = sellerDto.getSellerKey();

    if (!sellerKey.equals(keyOfSeller)) {
      return ResponseEntity.status(403).body("Access Denied");
    }

    SellerDto updated = sellerService.edit(editDto);

    return ResponseEntity.ok(updated);
  }

  /**
   * 회원 탈퇴 : 본인만 가능
   *
   * @param sellerKey
   * @param token
   * @return
   */
  @DeleteMapping("/{sellerKey}")
  public ResponseEntity<?> deleteCustomer(@PathVariable String sellerKey,
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

    SellerDto sellerDto = sellerService.findByEmail(email);
    String keyOfSeller = sellerDto.getSellerKey();

    if (!sellerKey.equals(keyOfSeller)) {
      return ResponseEntity.status(403).body("Access Denied");
    }

    sellerKey = sellerService.delete(sellerKey);

    return ResponseEntity.ok("delete " + sellerKey);
  }
}
