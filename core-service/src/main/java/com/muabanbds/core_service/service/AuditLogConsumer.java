package com.muabanbds.core_service.service;

import com.muabanbds.common_service.dto.identityDto.request.AuditLogRequest;
import org.springframework.kafka.support.Acknowledgment;

public interface AuditLogConsumer {
    void consumeAuditLog(AuditLogRequest auditLogRequest, Acknowledgment ack);
}
