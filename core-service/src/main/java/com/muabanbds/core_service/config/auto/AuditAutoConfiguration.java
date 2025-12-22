package com.muabanbds.core_service.config.auto;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@AutoConfiguration
@ConditionalOnClass(EntityManager.class)
@ConditionalOnProperty(
        prefix = "core",
        name = "entity",
        havingValue = "true"
)
public class AuditAutoConfiguration {
}
