package yeri_nihongo.exception.review;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class ReviewNotFoundException extends BaseException {

    public ReviewNotFoundException(Long reviewId) {
        super("Review not found with id: " + reviewId, "REVIEW_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
