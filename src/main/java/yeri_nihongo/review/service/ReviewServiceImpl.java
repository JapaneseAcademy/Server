package yeri_nihongo.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.review.converter.ReviewConverter;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewForAdminResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;
import yeri_nihongo.review.dto.response.ReviewResponse;
import yeri_nihongo.review.repository.ReviewRepository;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final CommonService commonService;

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewByReviewId(Long reviewId) {
        Review review = commonService.getReviewByReviewId(reviewId);

        return getReviewDetailResponse(review, ReviewConverter::toReviewDetailResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsByCourseInfoId(Long courseInfoId, Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        return processReview(() ->
                reviewRepository.getReviewByCourseInfoId(courseInfoId, pageable), pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getBestReviewsByCourseInfoId(Long courseInfoId, Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        return processReview(() ->
                reviewRepository.getBestReviewByCourseInfoId(courseInfoId, pageable), pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDetailResponse> getMainReviewsByCourse() {
        List<Review> reviews = reviewRepository.getMainReviewByCourseInfoId();
        List<ReviewDetailResponse> responses = reviews.stream()
                .map(review -> getReviewDetailResponse(review, ReviewConverter::toReviewDetailResponse))
                .toList();

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewForAdminResponse> getReviewsForAdmin() {
        List<Review> reviews = reviewRepository.findAllByOrderByCreatedAtDesc();
        List<ReviewForAdminResponse> responses = reviews.stream()
                .map(review -> (ReviewForAdminResponse) getReviewDetailResponse(review, ReviewConverter::toReviewForAdminResponse))
                .toList();

        return responses;
    }

    @Override
    @Transactional
    public void toggleVisibility(Long reviewId) {
        Review review = commonService.getReviewByReviewId(reviewId);
        review.toggleVisibility();
    }

    @Override
    @Transactional
    public void toggleBest(Long reviewId) {
        Review review = commonService.getReviewByReviewId(reviewId);
        review.toggleBest();
    }

    @Override
    @Transactional
    public void toggleForMain(Long reviewId) {
        Review review = commonService.getReviewByReviewId(reviewId);
        review.toggleForMain();
    }

    private ReviewListResponse processReview(Supplier<Page<Review>> supplier, Pageable pageable) {
        Page<Review> reviewProjections = supplier.get();
        List<ReviewResponse> responses = reviewProjections.stream()
                .map(reviewProjection -> {
                    List<String> imageUrls = reviewRepository.getImageUrlsByReviewId(reviewProjection.getId());
                    String writer = getNameByReviewId(reviewProjection.getId());
                    return ReviewConverter.toReviewResponse(reviewProjection, imageUrls, writer);
                })
                .toList();

        PageImpl<ReviewResponse> reviewResponses = new PageImpl<>(responses, pageable, reviewProjections.getTotalElements());
        return ReviewConverter.toReviewListResponse(reviewResponses);
    }

    private String getNameByReviewId(Long reviewId) {
        if (commonService.getReviewByReviewId(reviewId).getIsAnonymous()) {
            return "*****";
        }
        return reviewRepository.getNameByReviewId(reviewId);
    }

    private ReviewDetailResponse getReviewDetailResponse(
            Review review,
            QuadFunction<CourseInfo, Review, List<String>, String, ReviewDetailResponse> applier
    ) {
        CourseInfo courseInfo = commonService.getCourseInfoByCourseInfoId(review.getCourseInfo().getId());
        List<String> imageUrls = reviewRepository.getImageUrlsByReviewId(review.getId());
        String writer = getNameByReviewId(review.getId());

        return applier.apply(courseInfo, review, imageUrls, writer);
    }

    @FunctionalInterface
    private interface QuadFunction<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
    }
}
