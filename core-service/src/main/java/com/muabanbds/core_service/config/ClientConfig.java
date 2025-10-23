package com.muabanbds.core_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

    @LoadBalanced
    @Bean
    @Primary
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }
}
