package yeri_nihongo.review.service;

import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.review.dto.request.ReviewCreateRequest;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewListForAdminResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;

import java.util.List;

public interface ReviewService {

    ReviewDetailResponse getReviewByReviewId(Long reviewId);

    ReviewListResponse getReviewsByCourseInfoId(Long courseInfoId, Integer page);

    List<ReviewDetailResponse> getMainReviews();

    ReviewListForAdminResponse getAllReviewsForAdmin(Integer page);

    ReviewListForAdminResponse getReviewsByCourseInfoIdForAdmin(Long courseInfoId, Integer page);

    void toggleVisibility(Long reviewId);

    void toggleBest(Long reviewId);

    void toggleForMain(Long reviewId);

    ReviewListResponse getAllReviews(Integer page);

    void createReview(ReviewCreateRequest request, List<MultipartFile> images);

    void deleteReview(Long reviewId);

    ReviewListResponse getMyReviews(Integer page);
}
