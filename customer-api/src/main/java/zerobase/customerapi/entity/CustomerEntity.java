package zerobase.customerapi.entity;

import static zerobase.common.type.Role.CUSTOMER;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase.common.entity.BaseEntity;
import zerobase.common.type.Role;
import zerobase.customerapi.dto.customer.CustomerEditDto;

/**
 * 고객 엔티티 : 아이디, 고객 키, 이메일, 비밀번호, 이름, 전화번호, 주소, 생일, 포인트, 역할
 */
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "customer_key")})
@Entity(name = "Customer")
public class CustomerEntity extends BaseEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, name = "customer_key")
  private String customerKey;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String phone;

  @Column(nullable = false)
  private String address;

  @ColumnDefault("0")
  private Long point;

  @Column(nullable = false)
  private LocalDate birth;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Builder
  public CustomerEntity(String customerKey, String email, String password, String name,
      String phone, String address, Long point, LocalDate birth) {
    this.customerKey = customerKey;
    this.email = email;
    this.password = password;
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.point = point;
    this.birth = birth;
    this.role = CUSTOMER;
  }

  /**
   * 고객 정보 수정 기능
   *
   * @param customerEditDto
   */
  public void updateCustomer(CustomerEditDto customerEditDto) {
    this.name = customerEditDto.getName();
    this.phone = customerEditDto.getPhone();
    this.address = customerEditDto.getAddress();
    this.birth = customerEditDto.getBirth();
  }

  /**
   * 포인트 충전 기능
   *
   * @param point
   */
  public void plusPoint(Long point) {
    this.point += point;
  }

  public void minusPoint(Long point) {
    this.point -= point;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role.getKey()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
