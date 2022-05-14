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
import com.yapily.sdk.model.PaymentRequest;
import com.yapily.sdk.model.PaymentResponse;

@Configuration
public class RedisConfiguration {

    public RedisConfiguration(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public ReactiveRedisTemplate<String, PaymentRequest> pendingPaymentTemplate(ObjectMapper objectMapper, ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<PaymentRequest> serializer = new Jackson2JsonRedisSerializer<>(PaymentRequest.class);
        serializer.setObjectMapper(objectMapper);
        RedisSerializationContext.RedisSerializationContextBuilder<String, PaymentRequest> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, PaymentRequest> context = builder.hashValue(serializer).build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, PaymentResponse> paymentResponseTemplate(ObjectMapper objectMapper, ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<PaymentResponse> serializer = new Jackson2JsonRedisSerializer<>(PaymentResponse.class);
        serializer.setObjectMapper(objectMapper);
        RedisSerializationContext.RedisSerializationContextBuilder<String, PaymentResponse> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, PaymentResponse> context = builder.hashValue(serializer).build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

}
