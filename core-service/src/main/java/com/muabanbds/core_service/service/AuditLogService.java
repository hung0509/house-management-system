package com.muabanbds.core_service.service;


import com.muabanbds.common_service.dto.identityDto.request.AuditLogRequest;
import com.muabanbds.common_service.dto.AuditLogResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;

import java.util.List;

public interface AuditLogService {
    ApiResponsePagination<List<AuditLogResponse>> getAllAuditLog(AuditLogRequest auditLogRequest);
}
