package yeri_nihongo.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.time.domain.TimeTable;

import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("SELECT tt " +
            "FROM TimeTable tt " +
            "WHERE tt.course.id = :courseId")
    List<TimeTable> findTimeTablesByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT ci " +
            "FROM TimeTable tt " +
            "JOIN Course c ON tt.course.id = c.id " +
            "JOIN CourseInfo ci ON c.courseInfo.id = ci. id " +
            "WHERE tt.id = :timeTableId")
    CourseInfo findCourseInfoByTimeTableId(@Param("timeTableId") Long timeTableId);

    @Query("SELECT e " +
            "FROM TimeTable tt " +
            "JOIN Enrollment e ON tt.id = e.timeTable.id " +
            "JOIN FETCH Member m ON e.member.id = m.id " +
            "WHERE tt.id = :timeTableId " +
            "ORDER BY m.name ASC")
    List<Enrollment> findEnrollmentsByTimeTableId(@Param("timeTableId") Long timeTableId);

    @Query("SELECT c.cost " +
            "FROM TimeTable tt " +
            "JOIN Course c ON tt.course.id = c.id " +
            "WHERE tt.id = :timeTableId")
    int findSaleCostByTimeTableId(@Param("timeTableId") Long timeTableId);

    @Query("SELECT m.id " +
            "FROM TimeTable tt " +
            "JOIN Enrollment e ON tt.id = e.timeTable.id " +
            "JOIN Member m ON e.member.id = m.id " +
            "WHERE tt.id = :timeTableId")
    List<Long> findMemberIdsByTimeTableId(@Param("timeTableId") Long timeTableId);

    @Query("SELECT c " +
            "FROM TimeTable tt " +
            "JOIN Course c ON tt.course.id = c.id " +
            "WHERE tt.id = :timeTableId")
    Course findCourseByTimeTableId(@Param("timeTableId") Long timeTableId);
}
