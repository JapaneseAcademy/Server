package yeri_nihongo.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ReviewResponse {

    private Long reviewId;
    private String reviewTitle;
    private String review;
    private List<String> imageUrls;
    private String writer;
    private LocalDate createdDate;
    private boolean isBest;
}
