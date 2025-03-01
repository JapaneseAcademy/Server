package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c " +
            "FROM Course c " +
            "WHERE c.courseInfo.id = :courseInfoId")
    List<Course> findCoursesByCourseInfoId(@Param("courseInfoId") Long courseInfoId);
}
