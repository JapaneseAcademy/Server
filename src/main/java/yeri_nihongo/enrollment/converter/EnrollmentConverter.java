package yeri_nihongo.enrollment.converter;

import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.domain.Category;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.exception.enrollment.EnrollmentMappingException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.payment.dto.response.TossPaymentConfirmResponse;
import yeri_nihongo.time.domain.TimeTable;

import java.time.LocalTime;

public class EnrollmentConverter {

    public static Enrollment toEntity(
            Member member, TimeTable timeTable, CustomEnrollmentRequest request
    ) {
        return Enrollment.builder()
                .member(member)
                .timeTable(timeTable)
                .category(request.getCategory())
                .paymentAmount(request.getPaymentAmount())
                .paymentAt(request.getPaymentDate().atTime(LocalTime.now()))
                .paymentKey("paymentKey")
                .orderId("orderId")
                .method(request.getMethod())
                .build();
    }

    public static Enrollment toEntity(
            Member member, TimeTable timeTable, Category category, TossPaymentConfirmResponse tossPaymentConfirmResponse
    ) {
        return Enrollment.builder()
                .member(member)
                .timeTable(timeTable)
                .category(category)
                .paymentAt(tossPaymentConfirmResponse.getPaymentAt())
                .paymentAmount(tossPaymentConfirmResponse.getPaymentAmount())
                .paymentKey(tossPaymentConfirmResponse.getPaymentKey())
                .orderId(tossPaymentConfirmResponse.getOrderId())
                .method(tossPaymentConfirmResponse.getMethod())
                .build();
    }

    public static EnrollmentListResponse toEnrollmentListResponse(
            Enrollment enrollment, CourseInfo courseInfo, boolean isReviewed
    ) {
        try {
            return EnrollmentListResponse.builder()
                    .enrollmentId(enrollment.getId())
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .mainImageUrl(courseInfo.getMainImageUrl())
                    .paymentAmount(enrollment.getPaymentAmount())
                    .paymentDate(enrollment.getPaymentAt().toLocalDate())
                    .category(enrollment.getCategory())
                    .isReviewed(isReviewed)
                    .build();
        } catch (Exception e) {
            throw new EnrollmentMappingException();
        }
    }
}
