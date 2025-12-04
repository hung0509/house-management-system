package com.muabanbds.common_service.dto.identityDto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    Integer id;
    String username;
    String password;
    Integer userId;
    Integer roleId;
    List<RoleResponse> roles;
}
