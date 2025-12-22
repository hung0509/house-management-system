package com.muabanbds.core_service.config.auto;

import com.muabanbds.core_service.config.HeaderExtractFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)

@ConditionalOnProperty(
        prefix = "core",
        name = "extract-header",
        havingValue = "true"
)
public class WebAuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(HeaderExtractFilter.class)
    public HeaderExtractFilter headerExtractFilter() {
        return new HeaderExtractFilter();
    }

    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    public FilterRegistrationBean<HeaderExtractFilter> auditHeaderFilterRegistration(
            HeaderExtractFilter filter
    ) {
        FilterRegistrationBean<HeaderExtractFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addUrlPatterns("/*");

        return registration;
    }
}

