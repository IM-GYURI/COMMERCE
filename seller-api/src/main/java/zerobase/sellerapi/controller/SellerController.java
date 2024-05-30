package zerobase.sellerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
