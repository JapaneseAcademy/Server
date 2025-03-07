package yeri_nihongo.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.time.domain.TimeTable;

import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("SELECT tt " +
            "FROM TimeTable tt " +
            "WHERE tt.course.id = :courseId")
    List<TimeTable> findTimeTablesByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT tt.studentCount " +
            "FROM TimeTable tt " +
            "WHERE tt.id = :timeTableId")
    int findStudentCountByTimeTableId(@Param("timeTableId") Long timeTableId);

    @Query("SELECT ci " +
            "FROM TimeTable tt " +
            "JOIN Course c ON tt.course.id = c.id " +
            "JOIN CourseInfo ci ON c.courseInfo.id = ci. id " +
            "WHERE tt.id = :timeTableId")
    CourseInfo findCourseInfoByTimeTableId(@Param("timeTableId") Long timeTableId);
}
