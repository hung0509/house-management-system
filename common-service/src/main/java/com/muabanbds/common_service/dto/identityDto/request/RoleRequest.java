package com.muabanbds.common_service.dto.identityDto.request;

import com.muabanbds.common_service.dto.BaseQueryRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest extends BaseQueryRequest {
    Integer id;
    String name;
    String code;
    String description;
    List<String> permissions;
}
