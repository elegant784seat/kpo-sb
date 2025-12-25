package com.hse.gozon.gateway.config;

import feign.Contract;
import feign.Feign;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder().errorDecoder(new CustomDecoder());
    }

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

}
