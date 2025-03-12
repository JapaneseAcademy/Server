package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class DuplicateEnrollmentException extends BaseException {
    public DuplicateEnrollmentException(Long memberId, Long timeTableId) {
        super(
                "Enrollment already exists with memberId: " + memberId + ", timeTableId: " + timeTableId,
                "DUPLICATE_ENROLLMENT_ERROR",
                HttpStatus.BAD_REQUEST
        );
    }
}
