package yeri_nihongo.payment.service;

import yeri_nihongo.payment.dto.request.TossPaymentConfirmRequest;

public interface TossService {

    void confirmPayment(String paymentKey, TossPaymentConfirmRequest request);
}
