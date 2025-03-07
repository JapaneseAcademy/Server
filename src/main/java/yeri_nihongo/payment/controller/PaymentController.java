package yeri_nihongo.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.payment.dto.request.TossPaymentConfirmRequest;
import yeri_nihongo.payment.service.TossService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final TossService tossService;

    @PostMapping("/toss/{paymentKey}")
    public ResponseEntity<HttpStatus> confirmTossPayment(
            @PathVariable("paymentKey") String paymentKey,
            @RequestBody @Valid TossPaymentConfirmRequest request
    ) {
        tossService.confirmPayment(paymentKey, request);

        return ResponseEntity.ok().build();
    }
}
