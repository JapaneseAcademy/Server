package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class EnrollmentNotFoundException extends BaseException {
    public EnrollmentNotFoundException(Long enrollmentId) {
        super("Enrollment not found with id: " + enrollmentId, "ENROLLMENT_NOT_FOUND_ERROR", HttpStatus.NOT_FOUND);
    }
}
