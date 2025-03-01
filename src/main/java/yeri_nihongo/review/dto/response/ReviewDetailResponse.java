package yeri_nihongo.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ReviewDetailResponse {

    private Long courseInfoId;
    private String courseTitle;
    private String reviewTitle;
    private String review;
    private List<String> imageUrls;
    private String writer;
    private LocalDate createdDate;
}
