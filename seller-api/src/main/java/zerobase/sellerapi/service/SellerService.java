package zerobase.sellerapi.service;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;
import static zerobase.sellerapi.exception.SellerErrorCode.SELLER_ALREADY_EXISTS;
import static zerobase.sellerapi.exception.SellerErrorCode.SELLER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.util.KeyGenerator;
import zerobase.sellerapi.dto.seller.EditDto;
import zerobase.sellerapi.dto.seller.SellerDto;
import zerobase.sellerapi.dto.seller.SellerSignInDto;
import zerobase.sellerapi.dto.seller.SellerSignUpDto;
import zerobase.sellerapi.entity.SellerEntity;
import zerobase.sellerapi.repository.SellerRepository;
import zerobase.sellerapi.security.TokenProvider;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository sellerRepository;
  private final PasswordEncoder passwordEncoder;
  private final KeyGenerator keyGenerator;
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  /**
   * 회원가입 : 이메일을 통해 이미 등록된 판매자인지 여부를 확인한 후 진행
   */
  @Transactional
  public SellerDto signUp(SellerSignUpDto signUpDto) {
    validateSellerExists(signUpDto.getEmail());

    SellerEntity savedSeller = sellerRepository.save(
        signUpDto.toEntity(
            keyGenerator.generateKey(),
            passwordEncoder.encode(signUpDto.getPassword())
        )
    );

    return SellerDto.fromEntity(savedSeller);
  }

  private void validateSellerExists(String email) {
    if (sellerRepository.existsByEmail(email)) {
      throw new CommonCustomException(SELLER_ALREADY_EXISTS);
    }
  }

  /**
   * 로그인 : 로그인 요청 정보를 spring security의 authenticate 메소드를 사용하여 검증
   */
  public SellerDto signIn(SellerSignInDto signInDto) {
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(
            signInDto.email(), signInDto.password()
        ));

    return SellerDto.fromEntity((SellerEntity) authentication.getPrincipal());
  }

  public SellerDto findByEmail(String email) {
    return SellerDto.fromEntity(sellerRepository.findByEmail(email)
        .orElseThrow(() -> new CommonCustomException(SELLER_NOT_FOUND)));
  }

  @Transactional
  public SellerDto edit(EditDto editDto) {
    SellerEntity seller = sellerRepository.findBySellerKey(editDto.getSellerKey())
        .orElseThrow(() -> new CommonCustomException(SELLER_NOT_FOUND));

    seller.updateSeller(editDto);

    return SellerDto.fromEntity(seller);
  }

  @Transactional
  public String delete(String sellerKey) {
    validateSellerExistsBySellerKey(sellerKey);

    sellerRepository.deleteBySellerKey(sellerKey);

    return sellerKey;
  }

  private void validateSellerExistsBySellerKey(String sellerKey) {
    if (!sellerRepository.existsBySellerKey(sellerKey)) {
      throw new CommonCustomException(SELLER_NOT_FOUND);
    }
  }

  public SellerDto validateAuthorizationAndGetSeller(String sellerKey, String email) {
    SellerDto sellerDto = findByEmail(email);
    String keyOfSeller = sellerDto.getSellerKey();

    if (!sellerKey.equals(keyOfSeller)) {
      throw new CommonCustomException(INVALID_REQUEST);
    }

    return sellerDto;
  }

  public SellerEntity findBySellerKeyOrThrow(String sellerKey) {
    return sellerRepository.findBySellerKey(sellerKey)
        .orElseThrow(() -> new CommonCustomException(SELLER_NOT_FOUND));
  }
}
