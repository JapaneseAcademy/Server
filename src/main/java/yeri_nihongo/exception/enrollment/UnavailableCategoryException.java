package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class UnavailableCategoryException extends BaseException {
    public UnavailableCategoryException(Long courseId) {
        super("Category unavailable with course id: " + courseId, "UNAVAILABLE_CATEGORY_ERROR", HttpStatus.BAD_REQUEST);
    }
}
