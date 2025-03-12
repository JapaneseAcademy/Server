package yeri_nihongo.time.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import yeri_nihongo.time.domain.Weekday;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TimeBlockCreateRequest {

    @NotNull(message = "강의 요일은 필수 입력 사항입니다.")
    private Weekday weekday;

    @NotNull(message = "강의 시작 시간은 필수 입력 사항입니다.")
    private LocalTime startTime;

    @NotNull(message = "강의 종료 시간은 필수 입력 사항입니다.")
    private LocalTime endTime;
}
