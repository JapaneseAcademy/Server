package yeri_nihongo.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeri_nihongo.review.dto.response.ReviewDetailResponse;
import yeri_nihongo.review.service.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewByReviewId(@PathVariable Long reviewId) {
        ReviewDetailResponse response = reviewService.getReviewByReviewId(reviewId);

        return ResponseEntity.ok(response);
    }
}
