package com.muabanbds.core_service.config.auto;

import com.muabanbds.core_service.helper.DbMetadataHelper;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(Table.class)

@ConditionalOnProperty(
        prefix = "core",
        name = "database",
        havingValue = "true"
)
public class DbMetadataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DbMetadataHelper.class)
    public DbMetadataHelper dbMetadataHelper() {
        return new DbMetadataHelper();
    }
}
