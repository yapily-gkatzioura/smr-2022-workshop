package com.yapily.rest.smr.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

import com.yapily.sdk.model.PaymentRequest;
import com.yapily.sdk.model.PaymentResponse;

import reactor.core.publisher.Mono;



@Repository
public class PaymentRepository {

    public static final String PENDING_KEY = "pending";
    public static final String EXECUTED_KEY = "executed";

    @Autowired
    private ReactiveRedisTemplate<String, PaymentRequest> pendingPaymentTemplate;

    @Autowired
    private ReactiveRedisTemplate<String, PaymentResponse> paymentResponseTemplate;

    public Mono<Boolean> storePending(String id, PaymentRequest paymentRequest) {
        return pendingPaymentTemplate.<String,PaymentRequest>opsForHash().put(PENDING_KEY, id, paymentRequest);
    }

    public Mono<PaymentRequest> getPending(String consent) {
        return pendingPaymentTemplate.<String,PaymentRequest>opsForHash().get(PENDING_KEY, consent);
    }

    public Mono<Boolean> storeExecuted(String id, PaymentResponse paymentResponse) {
        return paymentResponseTemplate.<String,PaymentResponse>opsForHash().put(PENDING_KEY, id, paymentResponse);
    }

    public Mono<PaymentResponse> getExecuted(String id) {
        return pendingPaymentTemplate.<String,PaymentResponse>opsForHash().get(PENDING_KEY, id);
    }


}
