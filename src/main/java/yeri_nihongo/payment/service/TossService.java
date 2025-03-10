package yeri_nihongo.payment.service;

import yeri_nihongo.payment.dto.response.OrderIdResponse;
import yeri_nihongo.payment.dto.response.TossPaymentConfirmResponse;

public interface TossService {

    TossPaymentConfirmResponse confirmPayment(String paymentKey, String orderId, int amount);

    OrderIdResponse generateOrderId(Long timeTableId);
}
