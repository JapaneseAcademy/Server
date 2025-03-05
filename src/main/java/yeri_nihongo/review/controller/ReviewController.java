package yeri_nihongo.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.dto.response.ReviewListResponse;
import yeri_nihongo.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewByReviewId(
            @PathVariable Long reviewId
    ) {
        ReviewDetailResponse response = reviewService.getReviewByReviewId(reviewId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/courses/{courseInfoId}/reviews")
    public ResponseEntity<ReviewListResponse> getReviewsByCourseInfoId(
            @PathVariable Long courseInfoId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListResponse response = reviewService.getReviewsByCourseInfoId(courseInfoId, page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews")
    public ResponseEntity<ReviewListResponse> getAllReviews(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListResponse response = reviewService.getAllReviews(page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/best")
    public ResponseEntity<ReviewListResponse> getBestReviewsByCourseInfoId(
            @RequestParam("courseInfoId") Long courseInfoId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) {
        ReviewListResponse response = reviewService.getBestReviewsByCourseInfoId(courseInfoId, page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/main")
    public ResponseEntity<List<ReviewDetailResponse>> getMainReviews() {
        List<ReviewDetailResponse> responses = reviewService.getMainReviews();

        return ResponseEntity.ok(responses);
    }
}
