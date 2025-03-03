package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.time.LocalDate;

@Getter
public class CourseForAdminResponse extends CourseBaseResponse {

    // TODO: 수업id, 제목, 시작날짜, 종료날짜, 타임테이블, 학생 수
    private String title;
    private int studentCount;
    private TimeTableResponse timeTable;

    @Builder
    CourseForAdminResponse(
            Long courseId,
            LocalDate startDate,
            LocalDate endDate,
            String title,
            int studentCount,
            TimeTableResponse timeTable
    ) {
        super(courseId, startDate, endDate);
        this.title = title;
        this.studentCount = studentCount;
        this.timeTable = timeTable;
    }
}
