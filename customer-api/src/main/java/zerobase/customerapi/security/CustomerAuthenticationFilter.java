package zerobase.customerapi.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT 인증 필터
 */
@RequiredArgsConstructor
@Component
public class CustomerAuthenticationFilter extends OncePerRequestFilter {

  public static final String TOKEN_PREFIX = "Bearer ";
  private final CustomerTokenProvider customerTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    String token = resolveToken(request);

    if (StringUtils.hasText(token) && customerTokenProvider.validateToken(token)) {
      Authentication authentication = customerTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION);

    if (ObjectUtils.isEmpty(token) || !token.startsWith(TOKEN_PREFIX)) {
      return null;
    }

    return token.substring(TOKEN_PREFIX.length());
  }
}