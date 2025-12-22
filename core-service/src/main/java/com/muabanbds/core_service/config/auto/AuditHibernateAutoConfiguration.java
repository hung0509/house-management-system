package com.muabanbds.core_service.config.auto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muabanbds.core_service.config.audit.AuditEntityInterceptor;
import com.muabanbds.core_service.helper.DbMetadataHelper;
import com.muabanbds.core_service.service.AuditLogProducer;
import jakarta.persistence.EntityManager;
import org.hibernate.Interceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@AutoConfiguration
@ConditionalOnClass({ Interceptor.class, EntityManager.class })
@ConditionalOnBean(AuditLogProducer.class)
@ConditionalOnProperty(
        prefix = "core",
        name = "interceptor",
        havingValue = "true"
)
public class AuditHibernateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditEntityInterceptor auditEntityInterceptor(
            Environment environment,
            AuditLogProducer auditLogProducer,
            ObjectMapper objectMapper,
            DbMetadataHelper dbMetadataHelper
    ) {
        return new AuditEntityInterceptor(
                environment,
                auditLogProducer,
                objectMapper,
                dbMetadataHelper
        );
    }

    @Bean
    public HibernatePropertiesCustomizer auditHibernateCustomizer(
            AuditEntityInterceptor interceptor
    ) {
        return hibernateProperties ->
                hibernateProperties.put(
                        "hibernate.session_factory.interceptor",
                        interceptor
                );
    }
}
