package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, CourseCustomRepository {

    @Query("SELECT ci.title " +
            "FROM Course c " +
            "JOIN c.courseInfo ci ON c.courseInfo.id = ci.id " +
            "WHERE c.id = :courseId")
    Optional<String> findCourseTitleByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT ci.cost " +
            "FROM Course c " +
            "JOIN CourseInfo ci ON c.courseInfo.id = ci.id " +
            "WHERE c.id = :courseId")
    Optional<Integer> findBaseCostByCourseId(@Param("courseId") Long courseId);
}
