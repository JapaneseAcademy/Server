package yeri_nihongo.enrollment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.enrollment.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("")
    public ResponseEntity<List<EnrollmentListResponse>> getEnrollmentsForUser() {
        List<EnrollmentListResponse> responses = enrollmentService.getEnrollmentsForUser();

        return ResponseEntity.ok(responses);
    }
}
