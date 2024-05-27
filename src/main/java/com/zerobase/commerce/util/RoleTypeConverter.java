package com.zerobase.commerce.util;

import com.zerobase.commerce.type.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 유저 권한 converter : Role -> String, String -> Role
 */

@Converter
public class RoleTypeConverter implements AttributeConverter<Role, String> {

  @Override
  public String convertToDatabaseColumn(Role role) {
    if (role == null) {
      return null;
    }

    return role.getKey();
  }

  @Override
  public Role convertToEntityAttribute(String s) {
    if (s == null) {
      return null;
    }

    return Role.fromKey(s);
  }
}
