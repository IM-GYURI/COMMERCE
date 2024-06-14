package zerobase.sellerapi.service;

import static zerobase.common.exception.CommonErrorCode.INVALID_REQUEST;
import static zerobase.sellerapi.exception.SellerErrorCode.SELLER_ALREADY_EXISTS;
import static zerobase.sellerapi.exception.SellerErrorCode.SELLER_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.common.exception.CommonCustomException;
import zerobase.common.util.KeyGenerator;
import zerobase.sellerapi.dto.SellerDto;
import zerobase.sellerapi.dto.SellerEditDto;
import zerobase.sellerapi.dto.SellerSignInDto;
import zerobase.sellerapi.dto.SellerSignUpDto;
import zerobase.sellerapi.entity.SellerEntity;
import zerobase.sellerapi.repository.SellerRepository;
import zerobase.sellerapi.security.SellerTokenProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {

  private final SellerRepository sellerRepository;
  private final PasswordEncoder passwordEncoder;
  private final KeyGenerator keyGenerator;
  private final SellerTokenProvider sellerTokenProvider;
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

    log.info("Seller sign up : " + savedSeller.getSellerKey());

    return SellerDto.fromEntity(savedSeller);
  }

  /**
   * 로그인 : 로그인 요청 정보를 spring security의 authenticate 메소드를 사용하여 검증
   */
  public SellerDto signIn(SellerSignInDto signInDto) {
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(new UsernamePasswordAuthenticationToken(
            signInDto.email(), signInDto.password()
        ));

    log.info("Seller sign in : " + ((SellerEntity) authentication.getPrincipal()).getSellerKey());

    return SellerDto.fromEntity((SellerEntity) authentication.getPrincipal());
  }


  /**
   * 판매자 정보 수정
   *
   * @param sellerEditDto
   * @return
   */
  @Transactional
  public SellerDto edit(SellerEditDto sellerEditDto) {
    SellerEntity seller = sellerRepository.findBySellerKey(sellerEditDto.getSellerKey())
        .orElseThrow(() -> new CommonCustomException(SELLER_NOT_FOUND));

    seller.updateSeller(sellerEditDto);

    log.info("Seller edited own information : " + sellerEditDto.getSellerKey());

    return SellerDto.fromEntity(seller);
  }

  /**
   * 회원 탈퇴
   *
   * @param sellerKey
   * @return
   */
  @Transactional
  public String delete(String sellerKey) {
    validateSellerExistsBySellerKey(sellerKey);

    sellerRepository.deleteBySellerKey(sellerKey);

    log.info("Seller deleted : " + sellerKey);

    return sellerKey;
  }

  /**
   * 이메일을 통해 판매자의 존재 여부 확인
   *
   * @param email
   */
  private void validateSellerExists(String email) {
    if (sellerRepository.existsByEmail(email)) {
      throw new CommonCustomException(SELLER_ALREADY_EXISTS);
    }
  }

  /**
   * 판매자 키를 통해 판매자의 존재 여부를 확인
   *
   * @param sellerKey
   */
  private void validateSellerExistsBySellerKey(String sellerKey) {
    if (!sellerRepository.existsBySellerKey(sellerKey)) {
      throw new CommonCustomException(SELLER_NOT_FOUND);
    }
  }

  /**
   * 판매자 키를 통해 판매자 본인인지 확인
   *
   * @param sellerKey
   * @param email
   * @return
   */
  public SellerDto validateAuthorizationAndGetSeller(String sellerKey, String email) {
    SellerDto sellerDto = findByEmail(email);
    String keyOfSeller = sellerDto.getSellerKey();

    if (!sellerKey.equals(keyOfSeller)) {
      throw new CommonCustomException(INVALID_REQUEST);
    }

    return sellerDto;
  }

  /**
   * 판매자 키를 통해 찾은 판매자의 엔티티를 반환
   *
   * @param sellerKey
   * @return
   */
  public SellerEntity findBySellerKeyOrThrow(String sellerKey) {
    return sellerRepository.findBySellerKey(sellerKey)
        .orElseThrow(() -> new CommonCustomException(SELLER_NOT_FOUND));
  }

  /**
   * 판매자 이메일을 통해 찾은 판매자의 Dto를 반환
   *
   * @param email
   * @return
   */
  public SellerDto findByEmail(String email) {
    return SellerDto.fromEntity(sellerRepository.findByEmail(email)
        .orElseThrow(() -> new CommonCustomException(SELLER_NOT_FOUND)));
  }
}
