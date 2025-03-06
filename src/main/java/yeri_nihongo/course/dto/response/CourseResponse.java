package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CourseResponse extends CourseBaseResponse {

    private List<TimeTableResponse> timeTables;

    @Builder
    public CourseResponse(Long courseId, LocalDate startDate, LocalDate endDate, List<TimeTableResponse> timeTables) {
        super(courseId, startDate, endDate);
        this.timeTables = timeTables;
    }
}
