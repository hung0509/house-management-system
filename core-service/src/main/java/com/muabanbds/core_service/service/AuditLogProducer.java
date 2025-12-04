package com.muabanbds.core_service.service;


import com.muabanbds.common_service.dto.identityDto.request.AuditLogRequest;

public interface AuditLogProducer {

    void sendAuditLog(AuditLogRequest auditLogRequest);
}
