package yeri_nihongo.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.review.converter.ReviewConverter;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
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

        if (review.getIsAnonymous()) {
            return ReviewConverter.toReviewDetailResponse(courseInfo, review, imageUrls, "익명");
        } else {
            String username = reviewRepository.getNameByReviewId(reviewId);
            return ReviewConverter.toReviewDetailResponse(courseInfo, review, imageUrls, username);
        }
    }
}
