package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(builderMethodName = "baseBuilder")
public class CourseBaseResponse {

    private Long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
}
