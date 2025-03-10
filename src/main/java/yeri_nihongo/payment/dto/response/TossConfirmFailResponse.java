package yeri_nihongo.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TossConfirmFailResponse {

    private String code;
    private String message;
}
