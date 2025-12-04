package com.muabanbds.core_service.config.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muabanbds.common_service.dto.identityDto.request.AuditLogRequest;
import com.muabanbds.common_service.helper.AuditContext;
import com.muabanbds.core_service.entity.AuditLog;
import com.muabanbds.core_service.entity.AuditLogUser;
import com.muabanbds.core_service.helper.DbMetadataHelper;
import com.muabanbds.core_service.service.AuditLogProducer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuditEntityInterceptor implements Interceptor {

    private final Environment environment;
    private final AuditLogProducer auditLogProducer;
    private final ObjectMapper objectMapper;

    // Map<entityName-rowId, Map<fieldName, FieldChange>>
    private final ThreadLocal<Map<String, Map<String, FieldChange>>> pendingChanges =
            ThreadLocal.withInitial(HashMap::new);

    // helper class to hold the first-old and last-new values
    @AllArgsConstructor
    private static class FieldChange {
        final String oldValue;
        String newValue;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {

        log.info("onFlushDirty triggered for entity: {}", entity.getClass().getSimpleName());

        if (entity instanceof AuditLog || entity instanceof AuditLogUser) {
            log.warn("Skipping audit log for UPDATE on AuditLog entity to prevent loop");
            return false;
        }

        String entityName = DbMetadataHelper.getTableName(entity);
        String entityKey = entityName + "-" + id;
        Map<String, Map<String, FieldChange>> txMap = pendingChanges.get();
        Map<String, FieldChange> fieldMap = txMap.computeIfAbsent(entityKey, k -> new HashMap<>());

        for (int i = 0; i < propertyNames.length; i++) {

            String propertyName = propertyNames[i];
            String fieldName = DbMetadataHelper.getColumnName(entity, propertyName);

//            if (loggedFields.contains(fieldName)) {
//                continue; // Skip if already logged in this transaction
//            }

            // entity skipped fields: created, updated, createdBy, createdBy, createdAt, updatedAt, createBy, updateBy
            if (fieldName.equalsIgnoreCase("created") ||
                    fieldName.equalsIgnoreCase("updated") ||
                    fieldName.equalsIgnoreCase("created_by") ||
                    fieldName.equalsIgnoreCase("updated_by") )
            {
                continue;
            }

            Object previousValue = previousState[i];
            Object currentValue = currentState[i];

            // Bỏ qua nếu cả hai giá trị đều null hoặc bằng nhau
            if (Objects.equals(previousValue, currentValue)) {
                continue;
            }

            // Xử lý đặc biệt cho trường kiểu Double hoặc BigDecimal
            if (previousValue instanceof Double prevDouble && currentValue instanceof Double currDouble) {
                if (Math.abs(prevDouble - currDouble) < 0.0001) {
                    log.debug("Skipping audit log for field {}: values {} and {} are numerically equivalent", fieldName, prevDouble, currDouble);
                    continue;
                }
            } else if (previousValue instanceof BigDecimal prevBigDecimal && currentValue instanceof BigDecimal currBigDecimal) {
                if (prevBigDecimal.compareTo(currBigDecimal) == 0) {
                    log.debug("Skipping audit log for field {}: values {} and {} are numerically equivalent", fieldName, prevBigDecimal, currBigDecimal);
                    continue;
                }
            }

            String oldValueString = previousValue != null ? previousValue.toString() : "null";
            String newValueString = currentValue != null ? currentValue.toString() : "null";
            FieldChange change = fieldMap.get(fieldName);
            if (change == null) {
                fieldMap.put(fieldName, new FieldChange(oldValueString, newValueString));
            } else {
                change.newValue = newValueString;
            }

        }
        return false;
    }

    // Clear the tracking map after the transaction
    @Override
    public  void afterTransactionCompletion(Transaction tx) {

        Map<String, Map<String, FieldChange>> txMap = pendingChanges.get();

        try {
            if (AuditContext.getAuditInfo() == null) {
                log.warn("AuditContext is null, skipping audit log for UPDATE for changes: {}", new ArrayList<>(txMap.keySet()));
                return;
            }

            String serviceName = environment.getProperty("spring.application.name", "unknown-service");
            Integer userId = AuditContext.getAuditInfo().getUserId();

            for (Map.Entry<String, Map<String, FieldChange>> rowMapEntry : txMap.entrySet()) {

                String entityName = rowMapEntry.getKey().split("-", 2)[0];
                Integer id = Integer.valueOf(rowMapEntry.getKey().split("-", 2)[1]);

                for (Map.Entry<String, FieldChange> fieldMapEntry : rowMapEntry.getValue().entrySet()) {

                    String fieldName = fieldMapEntry.getKey();
                    String oldValue = fieldMapEntry.getValue().oldValue;
                    String newValue = fieldMapEntry.getValue().newValue;

                    AuditLogRequest auditLog = buildAuditLogRequest(
                            serviceName,
                            entityName,
                            getStringId(id),
                            fieldName,
                            oldValue,
                            newValue,
                            "UPDATE",
                            userId
                    );

                    try {
                        auditLogProducer.sendAuditLog(auditLog);
//                        log.info("Audit log sent successfully for UPDATE on entity: {}, field: {}", entityName, fieldName);
                    } catch (Exception e) {
                        log.error("Failed to send audit log for UPDATE on entity: {}, field: {}, error: {}",
                                entityName, fieldName, e.getMessage(), e);
                    }
                }
            }

        } catch (Exception ignored) {
            log.info("Error from sending AuditLog: ", ignored);
        } finally {
            pendingChanges.remove();
//            super.afterTransactionCompletion(tx);
        }

    }


    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types){

        log.info("onSave triggered for entity: {}", entity.getClass().getSimpleName());

        if (entity instanceof AuditLog || entity instanceof AuditLogUser) {
            log.warn("Skipping audit log for INSERT on AuditLog entity to prevent loop");
            return false;
        }

        String entityName = DbMetadataHelper.getTableName(entity);
        if (AuditContext.getAuditInfo() == null) {
            log.warn("AuditContext is null, skipping audit log for INSERT on entity: {}", entityName);
            return false;
        }

        String serviceName = environment.getProperty("spring.application.name", "unknown-service");
        Integer userId = AuditContext.getAuditInfo().getUserId();


        AuditLogRequest auditLog = buildAuditLogRequest(
                serviceName,
                entityName,
                getStringId((Serializable) id),
                null,
                null,
                getStringId((Serializable) id),
                "INSERT",
                userId
        );

        try {
            auditLogProducer.sendAuditLog(auditLog);
//            log.info("Audit log sent successfully for INSERT on entity: {}", entityName);
        } catch (Exception e) {
            log.error("Failed to send audit log for INSERT on entity: {}, error: {}",
                    entityName, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types){

        log.info("onDelete triggered for entity: {}", entity.getClass().getSimpleName());

        if (entity instanceof AuditLog || entity instanceof AuditLogUser) {
            log.warn("Skipping audit log for DELETE on AuditLog entity to prevent loop");
            return;
        }

        String entityName = DbMetadataHelper.getTableName(entity);
        if (AuditContext.getAuditInfo() == null) {
            log.warn("AuditContext is null, skipping audit log for DELETE on entity: {}", entityName);
            return;
        }

        String serviceName = environment.getProperty("spring.application.name", "unknown-service");
        Integer userId = AuditContext.getAuditInfo().getUserId();


        AuditLogRequest auditLog = buildAuditLogRequest(
                serviceName,
                entityName,
                getStringId((Serializable) id),
                null,
                getStringId((Serializable) id),
                null,
                "DELETE",
                userId
        );

        try {
            auditLogProducer.sendAuditLog(auditLog);
//            log.info("Audit log sent successfully for DELETE on entity: {}", entityName);
        } catch (Exception e) {
            log.error("Failed to send audit log for DELETE on entity: {}, error: {}",
                    entityName, e.getMessage(), e);
        }
    }

    private AuditLogRequest buildAuditLogRequest(
            String serviceName,
            String entityName,
            String entityId,
            String fieldName,
            String oldValue,
            String newValue,
            String action,
            Integer userId
    ) {
        return AuditLogRequest.builder()
                .isForUser("N")
                .serviceName(serviceName)
                .entityName(entityName)
                .entityId(entityId)
                .fieldName(fieldName)
                .oldValue(oldValue)
                .newValue(newValue)
                .action(action)
                .userId(userId)
//                .timestamp(DateHelper.toInstantNowUTC().toString())
                .build();
    }

    private String getStringId(Serializable id) {
        if (id == null) {
            return "Id null";
        }
        if (id instanceof Integer) {
            return String.valueOf(id);
        } else {
            try {
                return objectMapper.writeValueAsString(id);
            } catch (Exception e) {
                log.error("Failed to call objectMapper.writeValueAsString(Serializable id) for id {}, error: ", id, e);
                return "objectMapper.writeValueAsString(id) threw";
            }
        }
    }

}
