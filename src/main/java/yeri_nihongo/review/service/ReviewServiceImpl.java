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
import yeri_nihongo.review.dto.response.ReviewListResponse;
import yeri_nihongo.review.dto.response.ReviewProjection;
import yeri_nihongo.review.dto.response.ReviewResponse;
import yeri_nihongo.review.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final CommonService commonService;

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewByReviewId(Long reviewId) {
        Review review = commonService.getReviewByReviewId(reviewId);
        List<String> imageUrls = reviewRepository.getImageUrlsByReviewId(review.getId());
        CourseInfo courseInfo = commonService.getCourseInfoByCourseInfoId(review.getCourseInfo().getId());

        String writer = getNameByReviewId(review.getId());
        return ReviewConverter.toReviewDetailResponse(courseInfo, review, imageUrls, writer);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsByCourseInfoId(Long courseInfoId, Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ReviewProjection> reviewProjections = reviewRepository.getReviewByCourseInfoId(courseInfoId, pageable);
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
            return "익명";
        }
        return reviewRepository.getNameByReviewId(reviewId);
    }
}
