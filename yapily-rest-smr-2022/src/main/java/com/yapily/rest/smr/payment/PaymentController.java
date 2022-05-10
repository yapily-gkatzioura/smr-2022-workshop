package com.yapily.rest.smr.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yapily.rest.smr.repository.PaymentRepository;
import com.yapily.sdk.api.AuthorisationsApi;
import com.yapily.sdk.api.PaymentsApi;
import com.yapily.sdk.model.ApiResponseOfPaymentAuthorisationRequestResponse;
import com.yapily.sdk.model.PaymentAuthorisationRequest;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private AuthorisationsApi authorisationsApi;

    @Autowired
    private PaymentsApi paymentsApi;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/{id}/status")
    public Mono<String> retrieve(@PathVariable String id) {
        return Mono.just(id);
    }

    @PostMapping("/")
    public Mono<ApiResponseOfPaymentAuthorisationRequestResponse> create(@RequestBody PaymentAuthorisationRequest paymentAuthorisationRequest) {
        return authorisationsApi.createPaymentAuthorisation(paymentAuthorisationRequest,null, null,null,false)
                .flatMap(a ->
                    paymentRepository.storePending(a).map( c -> a)
                );
    }


}
