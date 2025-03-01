package yeri_nihongo.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;
import yeri_nihongo.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewByReviewId(
            @PathVariable Long reviewId
    ) {
        ReviewDetailResponse response = reviewService.getReviewByReviewId(reviewId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ReviewListResponse> getReviewsByCourseInfoId(
            @RequestParam("courseInfoId") Long courseInfoId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListResponse response = reviewService.getReviewsByCourseInfoId(courseInfoId, page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/best")
    public ResponseEntity<ReviewListResponse> getBestReviewsByCourseInfoId(
            @RequestParam("courseInfoId") Long courseInfoId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListResponse response = reviewService.getBestReviewsByCourseInfoId(courseInfoId, page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/main")
    public ResponseEntity<List<ReviewDetailResponse>> getMainReviews() {
        List<ReviewDetailResponse> responses = reviewService.getMainReviewsByCourse();

        return ResponseEntity.ok(responses);
    }
}
