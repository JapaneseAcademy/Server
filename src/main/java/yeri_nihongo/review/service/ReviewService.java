package yeri_nihongo.review.service;

import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewForAdminResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;

import java.util.List;

public interface ReviewService {

    ReviewDetailResponse getReviewByReviewId(Long reviewId);

    ReviewListResponse getReviewsByCourseInfoId(Long courseInfoId, Integer page);

    ReviewListResponse getBestReviewsByCourseInfoId(Long courseInfoId, Integer page);

    List<ReviewDetailResponse> getMainReviewsByCourse();

    List<ReviewForAdminResponse> getReviewsForAdmin();

    void toggleVisibility(Long reviewId);
}
