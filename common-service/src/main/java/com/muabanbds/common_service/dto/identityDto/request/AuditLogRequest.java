package com.muabanbds.common_service.dto.identityDto.request;

import com.muabanbds.common_service.dto.identityDto.BaseQueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogRequest extends BaseQueryRequest implements Serializable {

    private String isForUser;
    private Integer id;
    private String serviceName;
    private String entityName;
    private Object entityId;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String action;
    private Integer userId;
    private String uuid;
    private String timestamp;
    private String description; // for auditLogUser (not used)

}
