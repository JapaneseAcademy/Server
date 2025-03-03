package yeri_nihongo.enrollment.service;

import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentListResponse> getEnrollmentsForUser();

    void createEnrollmentByAdmin(CustomEnrollmentRequest request);
}
