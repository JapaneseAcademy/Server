package yeri_nihongo.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReviewForAdminResponse extends ReviewDetailResponse{

    private boolean isForMain;
    private boolean isBest;
    private boolean isVisible;

    @Builder(builderMethodName = "adminBuilder")
    public ReviewForAdminResponse(
            Long reviewId,
            String reviewTitle,
            String review,
            List<String> imageUrls,
            String writer,
            LocalDate createdDate,
            Long courseInfoId,
            String courseTitle,
            boolean isForMain,
            boolean isBest,
            boolean isVisible
    ) {
        super(reviewId, reviewTitle, review, imageUrls, writer, createdDate, courseInfoId, courseTitle);
        this.isForMain = isForMain;
        this.isBest = isBest;
        this.isVisible = isVisible;
    }
}
