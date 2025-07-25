package com.muabanbds.common_service.helper;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditInfo {
    private Integer userId;
    private String username;
}
