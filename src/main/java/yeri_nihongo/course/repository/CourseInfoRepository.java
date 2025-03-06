package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.domain.Level;

import java.util.List;

@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo, Long> {

    @Query("SELECT ci " +
            "FROM CourseInfo ci " +
            "WHERE ci.level = :level")
    List<CourseInfo> findCourseInfosByLevel(@Param("level") Level level);
}
