package com.yapily.rest.smr.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

import com.yapily.sdk.model.PaymentRequest;

import reactor.core.publisher.Mono;



@Repository
public class PaymentRepository {

    public static final String PENDING_KEY = "pending";
    @Autowired
    private ReactiveRedisTemplate<String, PaymentRequest> pendingPaymentTemplate;

    public Mono<Boolean> storePending(String id, PaymentRequest paymentRequest) {
        return pendingPaymentTemplate.<String,PaymentRequest>opsForHash().put(PENDING_KEY, id, paymentRequest);
    }
    public Mono<PaymentRequest> getPending(String consent) {
        return pendingPaymentTemplate.<String,PaymentRequest>opsForHash().get(PENDING_KEY, consent);
    }
    public void removePending() {

    }

    public void storeComplete() {

    }

}
