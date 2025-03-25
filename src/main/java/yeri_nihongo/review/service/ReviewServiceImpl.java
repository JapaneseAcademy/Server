package yeri_nihongo.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.config.auth.PrincipalDetailsService;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.service.EnrollmentService;
import yeri_nihongo.exception.auth.UserForbiddenException;
import yeri_nihongo.review.converter.ReviewConverter;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.request.ReviewCreateRequest;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewForAdminResponse;
import yeri_nihongo.review.dto.response.ReviewListForAdminResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;
import yeri_nihongo.review.repository.ReviewRepository;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final CommonService commonService;
    private final EnrollmentService enrollmentService;
    private final ReviewImageService reviewImageService;

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
        Pageable pageable = PageRequest.of(page, 5, Sort.by(
                Sort.Order.desc("isBest"),
                Sort.Order.desc("createdAt")
        ));

        return processReview(() ->
                reviewRepository.getReviewByCourseInfoId(courseInfoId, pageable), pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDetailResponse> getMainReviews() {
        List<Review> reviews = reviewRepository.getMainReview();
        List<ReviewDetailResponse> responses = reviews.stream()
                .map(review -> getReviewDetailResponse(review, ReviewConverter::toReviewDetailResponse))
                .toList();

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListForAdminResponse getAllReviewsForAdmin(Integer page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> reviews = reviewRepository.findAll(pageable);

        List<ReviewForAdminResponse> responses = reviews.stream()
                .map(review -> (ReviewForAdminResponse) getReviewDetailResponse(review, ReviewConverter::toReviewForAdminResponse))
                .toList();

        PageImpl<ReviewForAdminResponse> reviewResponses = new PageImpl<>(responses, pageable, reviews.getTotalElements());
        return ReviewConverter.toReviewListForAdminResponse(reviewResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListForAdminResponse getReviewsByCourseInfoIdForAdmin(Long courseInfoId, Integer page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Review> reviews = reviewRepository.getReviewsByCourseInfoIdForAdmin(courseInfoId, pageable);

        List<ReviewForAdminResponse> responses = reviews.stream()
                .map(review -> (ReviewForAdminResponse) getReviewDetailResponse(review, ReviewConverter::toReviewForAdminResponse))
                .toList();

        PageImpl<ReviewForAdminResponse> reviewResponses = new PageImpl<>(responses, pageable, reviews.getTotalElements());
        return ReviewConverter.toReviewListForAdminResponse(reviewResponses);
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

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getAllReviews(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(
                Sort.Order.desc("isBest"),
                Sort.Order.desc("createdAt")
        ));

        return processReview(() ->
                reviewRepository.findAllVisibleReviews(pageable), pageable);
    }

    @Override
    @Transactional
    public void createReview(ReviewCreateRequest request, List<MultipartFile> images) {
        Enrollment enrollment = commonService.getEnrollmentByEnrollmentId(request.getEnrollmentId());
        validateOwner(enrollment);

        CourseInfo courseInfo = enrollmentService.getCourseInfoByEnrollmentId(enrollment.getId());
        Review entity = ReviewConverter.toEntity(request, courseInfo, enrollment);

        Review review = reviewRepository.save(entity);
        if (images != null && !images.isEmpty()) {
            reviewImageService.createReviewImages(review, images);
        }
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = commonService.getReviewByReviewId(reviewId);
        validateOwner(review.getEnrollment());

        reviewRepository.delete(review);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getMyReviews(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("createdAt")));
        Long memberId = PrincipalDetailsService.getCurrentMemberId();

        return processReview(() -> reviewRepository.findReviewsByMemberId(memberId, pageable), pageable);
    }

    private ReviewListResponse processReview(Supplier<Page<Review>> supplier, Pageable pageable) {
        Page<Review> reviews = supplier.get();
        List<ReviewDetailResponse> responses = reviews.stream()
                .map(review -> getReviewDetailResponse(review, ReviewConverter::toReviewDetailResponse))
                .toList();

        PageImpl<ReviewDetailResponse> reviewResponses = new PageImpl<>(responses, pageable, reviews.getTotalElements());
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

    private void validateOwner(Enrollment enrollment) {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        if (!memberId.equals(enrollment.getMember().getId())) {
            throw new UserForbiddenException(memberId);
        }
    }
}
