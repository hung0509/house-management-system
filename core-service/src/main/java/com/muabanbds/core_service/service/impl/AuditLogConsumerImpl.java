package com.muabanbds.core_service.service.impl;

import com.muabanbds.common_service.dto.identityDto.request.AuditLogRequest;
import com.muabanbds.common_service.helper.ParseHelper;
import com.muabanbds.core_service.entity.AbstractAuditLog;
import com.muabanbds.core_service.entity.AuditLog;
import com.muabanbds.core_service.entity.AuditLogUser;
import com.muabanbds.core_service.repository.AuditLogRepository;
import com.muabanbds.core_service.repository.AuditLogUserRepository;
import com.muabanbds.core_service.service.AuditLogConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@RequiredArgsConstructor
@Slf4j
public class AuditLogConsumerImpl implements AuditLogConsumer {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogUserRepository auditLogUserRepository;
    private static final String AUDIT_LOG_GROUP = "audit-log-group";
    private static final String AUDIT_LOG_TOPIC = "audit-log-topic";

    @KafkaListener(groupId = AUDIT_LOG_GROUP, topics = AUDIT_LOG_TOPIC, containerFactory = "auditLogKafkaListenerContainerFactory")
    public void consumeAuditLog(AuditLogRequest auditLogRequest, Acknowledgment ack) {

        String key = auditLogRequest.getEntityName() != null && auditLogRequest.getEntityId() != null
                ? auditLogRequest.getEntityName() + ":" + auditLogRequest.getEntityId()
                : "unknown-key";

        // Kiểm tra dữ liệu bắt buộc
        if (auditLogRequest.getEntityName() == null || auditLogRequest.getAction() == null) {
            log.error("Missing required fields: entityName or action, key={}, request={}", key, auditLogRequest);
            ack.acknowledge();
            return;
        }

        try {

            AbstractAuditLog auditLog = buildAuditLogFromRequestBuilder(auditLogRequest);
            if ("Y".equals(auditLogRequest.getIsForUser())) {
                auditLogUserRepository.save((AuditLogUser) auditLog);
            } else {
                auditLogRepository.save((AuditLog) auditLog);
            }

            ack.acknowledge();

        } catch (Exception e) {
            log.error("Failed to save audit log: key={}, request={}, error={}", key, auditLogRequest, e.getMessage(), e);
            ack.acknowledge();
        }
    }


    private AbstractAuditLog buildAuditLogFromRequestBuilder(AuditLogRequest auditLogRequest) {
        AbstractAuditLog.AbstractAuditLogBuilder<?, ?> builder;
        if ("Y".equals(auditLogRequest.getIsForUser())) {
            builder = AuditLogUser.builder()
                    .entityId(ParseHelper.INT.parse(auditLogRequest.getEntityId()))
                    .description(auditLogRequest.getDescription());
        } else {
            builder = AuditLog.builder()
                    .entityId(ParseHelper.STRING.parse(auditLogRequest.getEntityId()))
                    .fieldName(auditLogRequest.getFieldName())
                    .oldValue(auditLogRequest.getOldValue())
                    .newValue(auditLogRequest.getNewValue());
        }

        builder
                .serviceName(auditLogRequest.getServiceName() != null ? auditLogRequest.getServiceName() : "unknown-service")
                .entityName(auditLogRequest.getEntityName())
                .action(auditLogRequest.getAction())
                .userId(auditLogRequest.getUserId() != null ? auditLogRequest.getUserId() : 0);

        return builder.build();
    }

}
