package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.CourseInfo;

import java.util.List;

@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo, Long> {

    @Query("SELECT ci " +
            "FROM CourseInfo ci " +
            "ORDER BY ci.level ASC")
    List<CourseInfo> findAll();

    @Query("SELECT ci.cost " +
            "FROM CourseInfo ci " +
            "WHERE ci.id = :courseInfoId")
    int findCostByCourseInfoId(@Param("courseInfoId") Long courseInfoId);
}
