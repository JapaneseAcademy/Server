package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class MaxLiveEnrollmentException extends BaseException {
    public MaxLiveEnrollmentException(Long timeTableId) {
        super("Maximum number of live enrollments reached for timeTableId: " + timeTableId,
                "MAX_LIVE_ENROLLMENT_REACHED",
                HttpStatus.CONFLICT);
    }
}
