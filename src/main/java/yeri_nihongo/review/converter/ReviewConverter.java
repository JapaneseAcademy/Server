package yeri_nihongo.review.converter;

import org.springframework.data.domain.Page;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.exception.review.ReviewMappingException;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;
import yeri_nihongo.review.dto.response.ReviewProjection;
import yeri_nihongo.review.dto.response.ReviewResponse;

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

    public static ReviewResponse toReviewResponse(
            ReviewProjection review, List<String> imageUrls, String writer
    ) {
        try {
            return ReviewResponse.builder()
                    .reviewId(review.getId())
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

    public static ReviewListResponse toReviewListResponse(
            Page<ReviewResponse> responses
    ) {
        try {
            List<ReviewResponse> reviews = responses.toList();
            return ReviewListResponse.builder()
                    .reviews(reviews)
                    .listSize(reviews.size())
                    .totalPage(responses.getTotalPages())
                    .totalElements(responses.getTotalElements())
                    .isFirst(responses.isFirst())
                    .isLast(responses.isLast())
                    .build();
        } catch (Exception e) {
            throw new ReviewMappingException();
        }
    }
}
