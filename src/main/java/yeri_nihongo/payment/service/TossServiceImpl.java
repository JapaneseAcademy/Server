package yeri_nihongo.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import yeri_nihongo.common.service.RedisService;
import yeri_nihongo.enrollment.domain.Category;
import yeri_nihongo.enrollment.repository.EnrollmentRepository;
import yeri_nihongo.exception.enrollment.MaxLiveEnrollmentException;
import yeri_nihongo.exception.enrollment.TossConfirmFailedException;
import yeri_nihongo.payment.dto.response.OrderIdResponse;
import yeri_nihongo.payment.dto.response.TossConfirmFailResponse;
import yeri_nihongo.payment.dto.response.TossPaymentConfirmResponse;
import yeri_nihongo.time.service.TimeTableService;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TossServiceImpl implements TossService {

    @Value("${toss.test.secret.key}")
    private String tossSecretKey;

    public static final String TOSS_BASE_URL = "https://api.tosspayments.com";

    public static final String BASE_ORDER_ID = "YERI-JP-ORDER-TOSS-";

    private static final int MAX_LIVE_ENROLLMENT = 12;

    private final TimeTableService timeTableService;
    private final RedisService redisService;

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public TossPaymentConfirmResponse confirmPayment(String paymentKey, String orderId, int amount) {
        WebClient webClient = WebClient.builder().build();
        String encodedSecretKey = Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes());

        return webClient.post()
                .uri(TOSS_BASE_URL + "/v1/payments/confirm")
                .header("Authorization", "Basic " + encodedSecretKey)
                .header("Content-Type", "application/json")
                .bodyValue(buildRequestBody(paymentKey, orderId, amount))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(TossConfirmFailResponse.class)
                        .flatMap(failResponse -> {
                            log.info(failResponse.getCode());
                            log.info(failResponse.getMessage());
                            return Mono.error(new TossConfirmFailedException(failResponse.getMessage(), failResponse.getCode()));
                        }))
                .bodyToMono(TossPaymentConfirmResponse.class)
                .block();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderIdResponse generateOrderId(Long timeTableId) {
        validateLiveEnrollment(timeTableId);

        String orderId = BASE_ORDER_ID + UUID.randomUUID();
        int amount = timeTableService.getSaleCostByTimeTableId(timeTableId);

        redisService.saveOrderIdAndAmount(orderId, amount);

        return new OrderIdResponse(timeTableId, orderId, amount);
    }

    private Map<String, Object> buildRequestBody(String paymentKey, String orderId, int amount) {
        return Map.of(
                "paymentKey", paymentKey,
                "orderId", orderId,
                "amount", amount
        );
    }

    private void validateLiveEnrollment(Long timeTableId) {
        int liveCount = enrollmentRepository.countEnrollmentByTimeTableIdAndCategory(timeTableId, Category.LIVE);
        if (liveCount >= MAX_LIVE_ENROLLMENT) {
            throw new MaxLiveEnrollmentException(timeTableId);
        }
    }
}
