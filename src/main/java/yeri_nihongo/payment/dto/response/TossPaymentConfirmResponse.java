package yeri_nihongo.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
public class TossPaymentConfirmResponse {

    private String paymentKey;
    private String orderId;
    private LocalDateTime paymentAt;
    private int paymentAmount;
    private String method;

    @Builder
    public TossPaymentConfirmResponse(String paymentKey, String orderId, String approvedAt, int totalAmount, String method) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.paymentAt = OffsetDateTime.parse(approvedAt).toLocalDateTime();
        this.paymentAmount = totalAmount;
        this.method = method;
    }
}
