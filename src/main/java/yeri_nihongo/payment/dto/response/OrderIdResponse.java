package yeri_nihongo.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderIdResponse {

    private Long timeTableId;
    private String orderId;
    private int amount;
}
