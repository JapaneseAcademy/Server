package yeri_nihongo.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.course.dto.request.CourseFilter;
import yeri_nihongo.course.dto.response.CourseForAdminResponse;
import yeri_nihongo.course.service.CourseService;
import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.service.EnrollmentService;
import yeri_nihongo.member.dto.response.MemberForAdminResponse;
import yeri_nihongo.member.service.MemberService;
import yeri_nihongo.review.dto.response.ReviewForAdminResponse;
import yeri_nihongo.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final ReviewService reviewService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @PostMapping("/token")
    public ResponseEntity<?> isInstructor() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    public ResponseEntity<List<MemberForAdminResponse>> getAllStudents() {
        List<MemberForAdminResponse> responses = memberService.getAllStudents();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewForAdminResponse>> getReviewsForAdmin() {
        List<ReviewForAdminResponse> responses = reviewService.getReviewsForAdmin();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseForAdminResponse>> getCoursesForAdmin(
            @RequestParam("filter") CourseFilter filter
    ) {
        List<CourseForAdminResponse> responses = courseService.getCoursesForAdmin(filter);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reviews/visibility")
    public ResponseEntity<?> toggleVisibility(
            @RequestParam("reviewId") Long reviewId
    ) {
        reviewService.toggleVisibility(reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reviews/best")
    public ResponseEntity<?> toggleBest(
            @RequestParam("reviewId") Long reviewId
    ) {
        reviewService.toggleBest(reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reviews/main")
    public ResponseEntity<?> toggleForMain(
            @RequestParam("reviewId") Long reviewId
    ) {
        reviewService.toggleForMain(reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/enrollments")
    public ResponseEntity<?> createEnrollment(
            @RequestBody @Valid CustomEnrollmentRequest request
    ) {
        enrollmentService.createEnrollmentByAdmin(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
