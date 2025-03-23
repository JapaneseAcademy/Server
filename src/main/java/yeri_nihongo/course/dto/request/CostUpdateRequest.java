package yeri_nihongo.course.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CostUpdateRequest {

    @NotNull(message = "강의 판매 가격은 필수 입력 사항입니다.")
    @Min(value = 1, message = "강의 판매 가격은 최소 1원 이상입니다.")
    private Integer cost;
}
