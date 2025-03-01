package yeri_nihongo.review.service;

import yeri_nihongo.review.dto.response.ReviewDetailResponse;

public interface ReviewService {

    ReviewDetailResponse getReviewByReviewId(Long reviewId);
}
