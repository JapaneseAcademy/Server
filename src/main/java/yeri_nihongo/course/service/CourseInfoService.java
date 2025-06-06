package yeri_nihongo.course.service;

import yeri_nihongo.course.dto.response.CourseInfoResponse;
import yeri_nihongo.course.dto.response.CourseListForAdminResponse;
import yeri_nihongo.course.dto.response.CourseListResponse;
import yeri_nihongo.course.dto.response.CourseTitleResponse;

import java.util.List;

public interface CourseInfoService {

    CourseInfoResponse getCourseInfoByCourseInfoId(Long courseInfoId);

    List<CourseListResponse> getAllCourseInfos();

    List<CourseTitleResponse> getAllCourseTitles();

    List<CourseListForAdminResponse> getAllCourseInfosForAdmin();
}
