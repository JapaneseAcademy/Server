package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class EnrollmentMappingException extends BaseException {
    public EnrollmentMappingException() {
        super("Enrollment dto mapping error", "ENROLLMENT_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
