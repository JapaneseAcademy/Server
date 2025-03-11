package yeri_nihongo.time.service;

import yeri_nihongo.course.domain.Course;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.time.dto.response.TimeTableResponse;
import yeri_nihongo.time.dto.response.TimeTableStudentsResponse;

import java.util.List;

public interface TimeTableService {

    List<TimeTableResponse> getTimeTablesByCourseId(Long courseId);

    int getStudentCountByTimeTableId(Long timeTableId);

    List<Enrollment> getEnrollmentsByTimeTableId(Long timeTableId);

    int getSaleCostByTimeTableId(Long timeTableId);

    List<TimeTableStudentsResponse> getCourseStudents(Course course);
}
