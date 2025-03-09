package yeri_nihongo.enrollment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.enrollment.dto.request.CreateEnrollmentRequest;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.enrollment.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("")
    public ResponseEntity<List<EnrollmentListResponse>> getEnrollments() {
        List<EnrollmentListResponse> responses = enrollmentService.getEnrollments();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> createEnrollment(
            @RequestBody @Valid CreateEnrollmentRequest request
    ) {
        enrollmentService.createEnrollment(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
