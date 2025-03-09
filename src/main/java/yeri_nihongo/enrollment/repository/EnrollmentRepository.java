package yeri_nihongo.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.domain.Enrollment;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT e " +
            "FROM Enrollment e " +
            "WHERE e.member.id = :memberId " +
            "ORDER BY e.createdAt DESC")
    List<Enrollment> findEnrollmentByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT ci " +
            "FROM Enrollment e " +
            "JOIN e.timeTable tt " +
            "JOIN tt.course c " +
            "JOIN c.courseInfo ci " +
            "WHERE e.id = :enrollmentId")
    Optional<CourseInfo> findCourseInfoByEnrollmentId(@Param("enrollmentId") Long enrollmentId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Enrollment e " +
            "JOIN Review r ON e.id = r.enrollment.id " +
            "WHERE e.id = :enrollmentId")
    boolean existsReviewByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
}
