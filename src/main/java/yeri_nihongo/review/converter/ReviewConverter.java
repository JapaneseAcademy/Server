package yeri_nihongo.review.converter;

import org.springframework.data.domain.Page;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.exception.review.ReviewMappingException;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.request.ReviewCreateRequest;
import yeri_nihongo.review.dto.response.*;

import java.util.List;

public class ReviewConverter {

    public static Review toEntity(
            ReviewCreateRequest request, CourseInfo courseInfo, Enrollment enrollment
    ) {
        return Review.builder()
                .enrollment(enrollment)
                .courseInfo(courseInfo)
                .title(request.getTitle())
                .review(request.getReview())
                .isAnonymous(request.getIsAnonymous())
                .build();
    }

    public static ReviewDetailResponse toReviewDetailResponse(
            CourseInfo courseInfo, Review review, List<String> imageUrls, String writer
    ) {
        try {
            return ReviewDetailResponse.detailBuilder()
                    .courseInfoId(courseInfo.getId())
                    .courseTitle(courseInfo.getTitle())
                    .reviewId(review.getId())
                    .reviewTitle(review.getTitle())
                    .review(review.getReview())
                    .imageUrls(imageUrls)
                    .writer(writer)
                    .createdDate(review.getCreatedAt().toLocalDate())
                    .isBest(review.getIsBest())
                    .build();
        } catch (Exception e) {
            throw new ReviewMappingException();
        }
    }

    public static ReviewResponse toReviewResponse(
            Review review, List<String> imageUrls, String writer
    ) {
        try {
            return ReviewResponse.builder()
                    .reviewId(review.getId())
                    .reviewTitle(review.getTitle())
                    .review(review.getReview())
                    .imageUrls(imageUrls)
                    .writer(writer)
                    .createdDate(review.getCreatedAt().toLocalDate())
                    .isBest(review.getIsBest())
                    .build();
        } catch (Exception e) {
            throw new ReviewMappingException();
        }
    }

    public static ReviewListResponse toReviewListResponse(
            Page<ReviewDetailResponse> responses
    ) {
        try {
            List<ReviewDetailResponse> reviews = responses.toList();
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

    public static ReviewForAdminResponse toReviewForAdminResponse(
            CourseInfo courseInfo, Review review, List<String> imageUrls, String writer
    ) {
        try {
            return ReviewForAdminResponse.adminBuilder()
                    .courseInfoId(courseInfo.getId())
                    .courseTitle(courseInfo.getTitle())
                    .reviewId(review.getId())
                    .reviewTitle(review.getTitle())
                    .review(review.getReview())
                    .imageUrls(imageUrls)
                    .writer(writer)
                    .createdDate(review.getCreatedAt().toLocalDate())
                    .isForMain(review.getIsForMain())
                    .isBest(review.getIsBest())
                    .isVisible(review.getIsVisible())
                    .build();
        } catch (Exception e) {
            throw new ReviewMappingException();
        }
    }

    public static ReviewListForAdminResponse toReviewListForAdminResponse(Page<ReviewForAdminResponse> responses) {
        try {
            List<ReviewForAdminResponse> reviews = responses.toList();
            return ReviewListForAdminResponse.builder()
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
