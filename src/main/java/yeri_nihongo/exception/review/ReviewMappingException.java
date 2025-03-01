package yeri_nihongo.exception.review;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class ReviewMappingException extends BaseException {

    public ReviewMappingException() {
        super("Review dto mapping error", "REVIEW_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
