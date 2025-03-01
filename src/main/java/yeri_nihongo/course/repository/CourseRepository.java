package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
