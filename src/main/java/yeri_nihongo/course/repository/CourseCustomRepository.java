package yeri_nihongo.course.repository;

import yeri_nihongo.course.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseCustomRepository {

    List<Course> searchWithFilter(String date);

    Optional<Course> findCurrentCourseByCourseInfoId(Long courseInfoId);

    Optional<Integer> findSaleCostByCourseInfoId(Long courseInfoId);

    Optional<Course> findCourseByCourseInfoIdAndDate(Long courseInfoId, String date);
}
