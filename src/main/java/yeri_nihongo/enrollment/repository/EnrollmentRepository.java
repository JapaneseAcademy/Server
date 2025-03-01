package yeri_nihongo.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.dto.response.CourseInfoProjection;
import yeri_nihongo.enrollment.domain.Enrollment;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT e " +
            "FROM Enrollment e " +
            "WHERE e.member.id = :memberId")
    List<Enrollment> findEnrollmentByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT ci.title AS title, ci.mainImageUrl AS mainImageUrl " +
            "FROM Enrollment e " +
            "JOIN e.timeTable tt " +
            "JOIN tt.course c " +
            "JOIN c.courseInfo ci " +
            "WHERE e.id = :enrollmentId")
    CourseInfoProjection findCourseInfoByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
}
