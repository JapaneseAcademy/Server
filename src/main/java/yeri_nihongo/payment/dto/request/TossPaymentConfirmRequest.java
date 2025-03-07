package yeri_nihongo.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TossPaymentConfirmRequest {

    @NotEmpty(message = "orderId는 필수 입력 사항입니다.")
    private String orderId;

    @NotNull(message = "결제 금액(amount)은 필수 입력 사항입니다.")
    private Integer amount;
}
