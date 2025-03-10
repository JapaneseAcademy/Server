package yeri_nihongo.enrollment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import yeri_nihongo.enrollment.domain.Category;

@Getter
@AllArgsConstructor
public class CreateEnrollmentRequest {

    @NotNull(message = "수강할 강의 Id는 필수 입력 정보입니다.")
    private Long timeTableId;

    @NotNull(message = "강의를 수강할 방식은 필수 입력 정보입니다.")
    private Category category;

    @NotNull(message = "결제 금액은 필수 입력 정보입니다.")
    private int amount;

    @NotBlank(message = "paymentKey는 필수 입력 정보입니다.")
    private String paymentKey;

    @NotBlank(message = "orderId는 필수 입력 정보입니다.")
    private String orderId;
}
