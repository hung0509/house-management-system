package com.muabanbds.common_service.dto.identityDto.request;

import com.muabanbds.common_service.dto.BaseQueryRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest extends BaseQueryRequest {
    Integer id;
    String name;
    String code;
    String description;
}
