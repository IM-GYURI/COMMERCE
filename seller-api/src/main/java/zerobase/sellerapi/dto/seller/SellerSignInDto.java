package zerobase.sellerapi.dto.seller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 로그인 Dto
 *
 * @param email
 * @param password
 */
public record SellerSignInDto(
    @NotBlank
    @Email
    String email,

    @NotBlank
    String password
) {

}
