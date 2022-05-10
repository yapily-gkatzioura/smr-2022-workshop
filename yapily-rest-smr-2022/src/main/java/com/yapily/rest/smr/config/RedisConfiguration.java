package com.yapily.rest.smr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yapily.sdk.model.ApiResponseOfPaymentAuthorisationRequestResponse;

@Configuration
public class RedisConfiguration {

    public RedisConfiguration(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public ReactiveRedisTemplate<String, ApiResponseOfPaymentAuthorisationRequestResponse> pendingPaymentTemplate(ObjectMapper objectMapper, ReactiveRedisConnectionFactory connectionFactory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<ApiResponseOfPaymentAuthorisationRequestResponse> serializer = new Jackson2JsonRedisSerializer<>(ApiResponseOfPaymentAuthorisationRequestResponse.class);
        serializer.setObjectMapper(objectMapper);
        RedisSerializationContext.RedisSerializationContextBuilder<String, ApiResponseOfPaymentAuthorisationRequestResponse> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, ApiResponseOfPaymentAuthorisationRequestResponse> context = builder.hashValue(serializer).build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

}
