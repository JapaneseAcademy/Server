package yeri_nihongo.enrollment.service;

import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.dto.request.CreateEnrollmentRequest;
import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentListResponse> getEnrollments();

    void createEnrollmentByAdmin(CustomEnrollmentRequest request);

    CourseInfo getCourseInfoByEnrollmentId(Long enrollmentId);

    void createEnrollment(CreateEnrollmentRequest request);

    int getCountByTimeTableId(Long timeTableId);

    void deleteEnrollment(Long enrollmentId);
}
