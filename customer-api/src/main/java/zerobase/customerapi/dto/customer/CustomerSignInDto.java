package zerobase.customerapi.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 로그인 Dto
 *
 * @param email
 * @param password
 */
public record CustomerSignInDto(
    @NotBlank
    @Email
    String email,

    @NotBlank
    String password
) {

}
