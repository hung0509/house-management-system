package com.muabanbds.core_service.config.auto;

import com.muabanbds.core_service.sql.QueryEngine;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@AutoConfiguration
@ConditionalOnClass(DataSource.class)
@ConditionalOnProperty(
        prefix = "core",
        name = "query-engine",
        havingValue = "true"
)
public class QueryEngineAutoConfiguration {
    @Bean
    public QueryEngine queryEngine(DataSource dataSource) {
        return new QueryEngine(dataSource);
    }
}
