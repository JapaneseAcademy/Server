package yeri_nihongo.course.service;

import yeri_nihongo.course.dto.response.CourseInfoResponse;

public interface CourseInfoService {

    CourseInfoResponse getCourseInfoByCourseInfoId(Long courseInfoId);
}
