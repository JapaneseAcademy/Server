package yeri_nihongo.course.service;

import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.dto.request.CourseCreateRequest;
import yeri_nihongo.course.dto.response.CourseForAdminResponse;
import yeri_nihongo.course.dto.response.CourseResponse;
import yeri_nihongo.time.dto.response.TimeTableStudentsResponse;

import java.util.List;

public interface CourseService {

    List<CourseForAdminResponse> getCoursesForAdmin(String date);

    CourseResponse getCurrentCourseByCourseInfoId(Long courseInfoId);

    List<TimeTableStudentsResponse> getTimeTableStudentsList(String filter);

    void createCourse(CourseCreateRequest request);

    Course getCurrentCourseEntityByCourseInfoId(Long courseInfoId);

    boolean getExistsCurrentCourseByCourseInfoId(Long courseInfoId);
}
