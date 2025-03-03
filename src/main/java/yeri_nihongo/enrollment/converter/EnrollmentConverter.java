package yeri_nihongo.enrollment.converter;

import yeri_nihongo.enrollment.domain.Category;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.exception.enrollment.EnrollmentMappingException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.time.domain.TimeTable;

import java.time.LocalDate;
import java.time.LocalTime;

public class EnrollmentConverter {

    public static Enrollment toEntity(
            Member member, TimeTable timeTable, Category category, int paymentAmount, LocalDate paymentDate
    ) {
        return Enrollment.builder()
                .member(member)
                .timeTable(timeTable)
                .category(category)
                .paymentAmount(paymentAmount)
                .paymentAt(paymentDate.atTime(LocalTime.now()))
                .build();
    }

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
