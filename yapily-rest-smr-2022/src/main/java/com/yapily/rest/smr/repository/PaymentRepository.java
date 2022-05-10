package com.yapily.rest.smr.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.yapily.sdk.model.ApiResponseOfPaymentAuthorisationRequestResponse;

import reactor.core.publisher.Mono;



@Repository
public class PaymentRepository {

    public static final String PENDING_KEY = "pending";
    @Autowired
    private ReactiveRedisTemplate<String, ApiResponseOfPaymentAuthorisationRequestResponse> pendingPaymentTemplate;

    public Mono<Boolean> storePending(ApiResponseOfPaymentAuthorisationRequestResponse apiResponseOfPaymentAuthorisationRequestResponse) {
        return pendingPaymentTemplate.<String,ApiResponseOfPaymentAuthorisationRequestResponse>opsForHash().put(PENDING_KEY, apiResponseOfPaymentAuthorisationRequestResponse.getData().getInstitutionConsentId(), apiResponseOfPaymentAuthorisationRequestResponse);
    }

    public void removePending() {

    }

    public void storeComplete() {

    }

}
