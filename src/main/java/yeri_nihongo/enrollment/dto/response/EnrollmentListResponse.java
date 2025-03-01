package yeri_nihongo.enrollment.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.enrollment.domain.Category;

import java.time.LocalDate;

@Getter
@Builder
public class EnrollmentListResponse {

    private Long enrollmentId;
    private String title;
    private String mainImageUrl;
    private LocalDate paymentDate;
    private int paymentAmount;
    private Category category;
    
    // TODO: 결제 수단 추가
}
