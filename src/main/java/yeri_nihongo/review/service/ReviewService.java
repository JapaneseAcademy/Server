package yeri_nihongo.review.service;

import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;

public interface ReviewService {

    ReviewDetailResponse getReviewByReviewId(Long reviewId);

    ReviewListResponse getReviewsByCourseInfoId(Long courseInfoId, Integer page);

    ReviewListResponse getBestReviewByCourseInfoId(Long courseInfoId, Integer page);
}
