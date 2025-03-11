package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.repository.CourseInfoRepository;
import yeri_nihongo.course.repository.CourseRepository;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.repository.EnrollmentRepository;
import yeri_nihongo.exception.course.CourseInfoNotFoundException;
import yeri_nihongo.exception.course.CourseNotFoundException;
import yeri_nihongo.exception.enrollment.EnrollmentNotFoundException;
import yeri_nihongo.exception.member.UserNotFoundException;
import yeri_nihongo.exception.review.ReviewNotFoundException;
import yeri_nihongo.exception.time.TimeTableNotFoundException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.member.repository.MemberRepository;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.repository.ReviewRepository;
import yeri_nihongo.time.domain.TimeTable;
import yeri_nihongo.time.repository.TimeTableRepository;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseInfoRepository courseInfoRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;
    private final TimeTableRepository timeTableRepository;

    @Transactional(readOnly = true)
    public Member getMemberByMemberId(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
    }

    @Transactional(readOnly = true)
    public Enrollment getEnrollmentByEnrollmentId(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));
    }

    @Transactional(readOnly = true)
    public Review getReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @Transactional(readOnly = true)
    public Course getCourseByCourseId(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId)
                );
    }

    @Transactional(readOnly = true)
    public CourseInfo getCourseInfoByCourseInfoId(Long courseInfoId) {
        return courseInfoRepository.findById(courseInfoId)
                .orElseThrow(() -> new CourseInfoNotFoundException(courseInfoId));
    }

    @Transactional(readOnly = true)
    public TimeTable getTimeTableByTimeTableId(Long timeTableId) {
        return timeTableRepository.findById(timeTableId)
                .orElseThrow(() -> new TimeTableNotFoundException(timeTableId));
    }

    @Transactional(readOnly = true)
    public String getCourseTitleByCourseId(Long courseId) {
        return courseRepository.findCourseTitleByCourseId(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }
}
