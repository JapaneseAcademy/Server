package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.time.LocalDate;

@Getter
public class CourseForAdminResponse extends CourseBaseResponse {

    private String title;
    private int studentCount;
    private TimeTableResponse timeTable;

    @Builder
    CourseForAdminResponse(
            Long courseId,
            LocalDate startDate,
            LocalDate endDate,
            int baseCost,
            int saleCost,
            String title,
            int studentCount,
            TimeTableResponse timeTable
    ) {
        super(courseId, startDate, endDate, baseCost, saleCost);
        this.title = title;
        this.studentCount = studentCount;
        this.timeTable = timeTable;
    }
}
