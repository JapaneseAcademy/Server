package yeri_nihongo.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.admin.dto.request.MessageRequest;
import yeri_nihongo.admin.dto.response.MessageResponse;
import yeri_nihongo.admin.service.MessageService;
import yeri_nihongo.course.dto.request.CourseCreateRequest;
import yeri_nihongo.course.dto.response.CourseForAdminResponse;
import yeri_nihongo.course.dto.response.CourseListForAdminResponse;
import yeri_nihongo.course.service.CourseInfoService;
import yeri_nihongo.course.service.CourseService;
import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.service.EnrollmentService;
import yeri_nihongo.member.dto.response.CourseStudentResponse;
import yeri_nihongo.member.dto.response.MemberForAdminResponse;
import yeri_nihongo.member.service.MemberService;
import yeri_nihongo.review.dto.response.ReviewListForAdminResponse;
import yeri_nihongo.review.service.ReviewService;
import yeri_nihongo.time.dto.response.TimeTableStudentsResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final MemberService memberService;
    private final ReviewService reviewService;
    private final CourseService courseService;
    private final CourseInfoService courseInfoService;
    private final EnrollmentService enrollmentService;
    private final MessageService messageService;

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
    public ResponseEntity<ReviewListForAdminResponse> getAllReviewsForAdmin(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListForAdminResponse response = reviewService.getAllReviewsForAdmin(page);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/courses")
    public ResponseEntity<HttpStatus> createCourse(
            @RequestBody @Valid CourseCreateRequest request
    ) {
        courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/courses/{courseInfoId}/reviews")
    public ResponseEntity<ReviewListForAdminResponse> getReviewsForAdmin(
            @PathVariable Long courseInfoId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListForAdminResponse responses = reviewService.getReviewsByCourseInfoIdForAdmin(courseInfoId, page);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/courseInfos")
    public ResponseEntity<List<CourseListForAdminResponse>> getCourseInfosForAdmin() {
        List<CourseListForAdminResponse> responses = courseInfoService.getAllCourseInfosForAdmin();

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
    public ResponseEntity<HttpStatus> toggleVisibility(
            @RequestParam("reviewId") Long reviewId
    ) {
        reviewService.toggleVisibility(reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reviews/best")
    public ResponseEntity<HttpStatus> toggleBest(
            @RequestParam("reviewId") Long reviewId
    ) {
        reviewService.toggleBest(reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reviews/main")
    public ResponseEntity<HttpStatus> toggleForMain(
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

    @GetMapping("/courses/students")
    public ResponseEntity<List<CourseStudentResponse>> getStudentsByTimeTableId(
            @RequestParam("timeTableId") Long timeTableId
    ) {
        List<CourseStudentResponse> responses = memberService.getStudentsByTimeTableId(timeTableId);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/message")
    public ResponseEntity<MessageResponse> sendMessage(
            @RequestBody @Valid MessageRequest request
    ) {
        MessageResponse response = messageService.sendMessage(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/time-tables")
    public ResponseEntity<List<TimeTableStudentsResponse>> getTimeTableStudents(
            @RequestParam("date")
            @NotBlank(message = "날짜는 필수 입력 사항입니다.")
            @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "올바른 날짜 형식을 입력해주세요. (예: 2025-01)")
            String date
    ) {
        List<TimeTableStudentsResponse> responses = courseService.getTimeTableStudentsList(date);

        return ResponseEntity.ok(responses);
    }
}
