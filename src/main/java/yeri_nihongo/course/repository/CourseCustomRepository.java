package yeri_nihongo.course.repository;

import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.dto.request.CourseFilter;

import java.util.List;

public interface CourseCustomRepository {

    List<Course> searchWithFilter(CourseFilter filters);

    Course findCurrentCourseByCourseInfoId(Long courseInfoId);
}
