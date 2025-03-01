package yeri_nihongo.exception.review;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class DuplicateReviewException extends BaseException {
    public DuplicateReviewException(Long enrollmentId) {
        super("Review already exists with enrollment id: " + enrollmentId, "DUPLICATE_REVIEW_ERROR", HttpStatus.CONFLICT);
    }
}
