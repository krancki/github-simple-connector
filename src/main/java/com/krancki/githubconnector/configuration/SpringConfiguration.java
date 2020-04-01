package com.krancki.githubconnector.configuration;

import com.krancki.githubconnector.github.exception.GithubResponseErrorHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(GithubProperties.class)
class SpringConfiguration {
    @Bean
    public RestTemplate githubRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .errorHandler(new GithubResponseErrorHandler())
                .build();
    }
}