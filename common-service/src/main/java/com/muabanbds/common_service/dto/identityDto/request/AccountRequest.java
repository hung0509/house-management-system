package com.muabanbds.common_service.dto.identityDto.request;

import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
    Integer id;
    String username;
    String password;
    Integer userId;
    Integer roleId;
    UserRequest user;
    List<Integer> roles;
}
