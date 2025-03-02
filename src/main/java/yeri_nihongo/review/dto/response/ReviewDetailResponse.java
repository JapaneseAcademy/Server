package yeri_nihongo.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReviewDetailResponse extends ReviewResponse {

    private Long courseInfoId;
    private String courseTitle;

    @Builder(builderMethodName = "detailBuilder")
    public ReviewDetailResponse(Long reviewId, String reviewTitle, String review, List<String> imageUrls, String writer, LocalDate createdDate, Long courseInfoId, String courseTitle) {
        super(reviewId, reviewTitle, review, imageUrls, writer, createdDate);
        this.courseInfoId = courseInfoId;
        this.courseTitle = courseTitle;
    }
}
