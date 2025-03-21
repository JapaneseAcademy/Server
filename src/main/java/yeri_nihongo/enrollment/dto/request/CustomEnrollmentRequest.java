package yeri_nihongo.enrollment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import yeri_nihongo.enrollment.domain.Category;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CustomEnrollmentRequest {

    @NotNull(message = "수강할 강의 Id는 필수 입력 정보입니다.")
    private Long timeTableId;

    @NotNull(message = "수강할 사용자 Id는 필수 입력 정보입니다.")
    private Long memberId;

    @NotNull(message = "강의를 수강할 방식은 필수 입력 정보입니다.")
    private Category category;

    @NotNull(message = "결제 금액은 필수 입력 정보입니다.")
    private int paymentAmount;

    @NotNull(message = "결제일은 필수 입력 정보입니다.")
    private LocalDate paymentDate;

    @NotEmpty(message = "결제 방식은 필수 입력 정보입니다.")
    private String method;
}
