package com.yapily.rest.smr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yapily.sdk.ApiClient;
import com.yapily.sdk.api.AuthorisationsApi;
import com.yapily.sdk.api.ConsentsApi;
import com.yapily.sdk.api.PaymentsApi;

import io.swagger.annotations.Api;

@Configuration
public class YapilyConfiguration {

    @Bean
    public ApiClient apiClient(@Value("${yapily.api.key}") String key, @Value("${yapily.api.secret}") String secret) {
        ApiClient defaultClient = new ApiClient();
        defaultClient.setUsername(key);
        defaultClient.setPassword(secret);
        defaultClient.setBasePath("https://api.yapily.com");
        return defaultClient;
    }

    @Bean
    public AuthorisationsApi authorisationsApi(ApiClient apiClient) {
        return new AuthorisationsApi(apiClient);
    }

    @Bean
    public PaymentsApi paymentsApi(ApiClient apiClient) {
        return new PaymentsApi(apiClient);
    }

    @Bean
    public ConsentsApi consentsApi(ApiClient apiClient) {
        return new ConsentsApi(apiClient);
    }

}
