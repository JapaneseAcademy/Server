package yeri_nihongo.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import yeri_nihongo.payment.dto.request.TossPaymentConfirmRequest;
import yeri_nihongo.payment.dto.response.TossPaymentConfirmResponse;

import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TossServiceImpl implements TossService {

    @Value("${toss.test.secret.key}")
    private String tossSecretKey;

    public static final String TOSS_BASE_URL = "https://api.tosspayments.com";

    @Override
    public TossPaymentConfirmResponse confirmPayment(String paymentKey, TossPaymentConfirmRequest request) {
        WebClient webClient = WebClient.builder().build();
        String encodedSecretKey = Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes());

        return webClient.post()
                .uri(TOSS_BASE_URL + "/v1/payments/confirm")
                .header("Authorization", "Basic " + encodedSecretKey)
                .header("Content-Type", "application/json")
                .bodyValue(buildRequestBody(paymentKey, request))
                .retrieve()
                .bodyToMono(TossPaymentConfirmResponse.class)
                .block();
    }

    private Map<String, Object> buildRequestBody(String paymentKey, TossPaymentConfirmRequest request) {
        return Map.of(
                "paymentKey", paymentKey,
                "orderId", request.getOrderId(),
                "amount", request.getAmount()
        );
    }
}
