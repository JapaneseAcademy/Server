package yeri_nihongo.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.enrollment.domain.Category;

import java.time.LocalDate;

@Getter
@Builder
public class CourseStudentResponse {

    private String name;
    private String phone;
    private LocalDate paymentDate;
    private Long enrollmentId;
    private Category category;
}
