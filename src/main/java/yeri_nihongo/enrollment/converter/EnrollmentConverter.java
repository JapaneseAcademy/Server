package yeri_nihongo.enrollment.converter;

import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.exception.enrollment.EnrollmentMappingException;

public class EnrollmentConverter {

    public static EnrollmentListResponse toEnrollmentListResponse(
            Enrollment enrollment, String title, String mainImageUrl
    ) {
        try {
            return EnrollmentListResponse.builder()
                    .enrollmentId(enrollment.getId())
                    .title(title)
                    .mainImageUrl(mainImageUrl)
                    .paymentAmount(enrollment.getPaymentAmount())
                    .paymentDate(enrollment.getPaymentAt().toLocalDate())
                    .category(enrollment.getCategory())
                    .build();
        } catch (Exception e) {
            throw new EnrollmentMappingException();
        }
    }
}
