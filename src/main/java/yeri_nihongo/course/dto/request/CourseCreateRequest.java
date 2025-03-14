package yeri_nihongo.course.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import yeri_nihongo.time.dto.request.TimeBlockCreateRequest;

import java.util.List;

@Getter
@AllArgsConstructor
public class CourseCreateRequest {

    @NotNull(message = "등록할 강의 정보 id는 필수 입력 사항입니다.")
    private Long courseInfoId;

    @NotEmpty(message = "등록할 강의 일정은 필수 입력 사항입니다.")
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])$",
            message = "올바른 날짜 형식을 입력해주세요. (예: 2025-01)"
    )
    private String date;

    @NotNull(message = "강의 분반 시간은 필수 입력 사항입니다.")
    private List<TimeBlockCreateRequest> timeBlocks;
}
