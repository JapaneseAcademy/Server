package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.repository.CourseRepository;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.repository.EnrollmentRepository;
import yeri_nihongo.exception.course.CourseNotFoundException;
import yeri_nihongo.exception.enrollment.EnrollmentNotFoundException;
import yeri_nihongo.exception.member.UserNotFoundException;
import yeri_nihongo.exception.review.ReviewNotFoundException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.member.repository.MemberRepository;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;

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
        return courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );
    }
}
