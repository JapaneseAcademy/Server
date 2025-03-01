package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CourseResponse {

    private Long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TimeTableResponse> timeTables;
}
