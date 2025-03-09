package yeri_nihongo.course.service;

import yeri_nihongo.course.dto.response.CourseForAdminResponse;
import yeri_nihongo.course.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {

    List<CourseForAdminResponse> getCoursesForAdmin(String date);

    CourseResponse getCurrentCourseByCourseInfoId(Long courseInfoId);

    Integer findSaleCostByCourseInfoId(Long courseInfoId);
}
