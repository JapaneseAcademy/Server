package yeri_nihongo.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Validated
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
            @RequestParam("date")
            @NotBlank(message = "날짜는 필수 입력 사항입니다.")
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "올바른 날짜 형식을 입력해주세요. (예: 2025-01)")
            String date
    ) {
        List<CourseForAdminResponse> responses = courseService.getCoursesForAdmin(date);

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
