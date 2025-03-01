package yeri_nihongo.review.converter;

import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.exception.review.ReviewMappingException;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;

import java.util.List;

public class ReviewConverter {

    public static ReviewDetailResponse toReviewDetailResponse(
            CourseInfo courseInfo, Review review, List<String> imageUrls, String writer
    ) {
        try {
            return ReviewDetailResponse.builder()
                    .courseInfoId(courseInfo.getId())
                    .courseTitle(courseInfo.getTitle())
                    .reviewTitle(review.getTitle())
                    .review(review.getReview())
                    .imageUrls(imageUrls)
                    .writer(writer)
                    .createdDate(review.getCreatedAt().toLocalDate())
                    .build();
        } catch (Exception e) {
            throw new ReviewMappingException();
        }
    }
}
