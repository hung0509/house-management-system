package com.muabanbds.core_service.service.impl;

import com.muabanbds.common_service.dto.identityDto.request.AuditLogRequest;
import com.muabanbds.core_service.service.AuditLogProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



@RequiredArgsConstructor
@Slf4j
public class AuditLogProducerImpl implements AuditLogProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String AUDIT_LOG_TOPIC = "audit-log-topic";

    @Override
    public void sendAuditLog(AuditLogRequest auditLogRequest) {
        String key = auditLogRequest.getEntityName() + ":" + auditLogRequest.getEntityId();
        try {
            log.info("Preparing to send audit log with request {}", auditLogRequest);
            kafkaTemplate.send(AUDIT_LOG_TOPIC, key, auditLogRequest);
        } catch (Exception e) {
            log.error("Error sending audit log to Kafka: key={}, error={}", key, e.getMessage(), e);
//            sendToDlq(auditLogRequest, key);
        }
    }
}
