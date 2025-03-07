package yeri_nihongo.payment.service;

import yeri_nihongo.payment.dto.request.TossPaymentConfirmRequest;
import yeri_nihongo.payment.dto.response.TossPaymentConfirmResponse;

public interface TossService {

    TossPaymentConfirmResponse confirmPayment(String paymentKey, TossPaymentConfirmRequest request);
}
