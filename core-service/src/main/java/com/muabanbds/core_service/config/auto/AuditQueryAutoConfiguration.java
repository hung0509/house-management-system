package com.muabanbds.core_service.config.auto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muabanbds.core_service.service.AuditLogService;
import com.muabanbds.core_service.service.impl.AuditLogServiceImpl;
import com.muabanbds.core_service.sql.QueryEngine;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import jakarta.persistence.EntityManager;

@AutoConfiguration
@ConditionalOnClass(EntityManager.class)
@ConditionalOnProperty(prefix = "core", name = "log-service", havingValue = "true")
public class AuditQueryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuditLogService.class)
    public AuditLogService auditLogService(
            MessageSource messageSource,
            ModelMapper modelMapper,
            QueryEngine queryEngine,
            EntityManager entityManager,
            ObjectMapper objectMapper
    ) {
        return new AuditLogServiceImpl(
                messageSource,
                modelMapper,
                queryEngine,
                entityManager,
                objectMapper
        );
    }
}

