package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class CourseMappingException extends BaseException {
    public CourseMappingException() {
        super("Course dto mapping error", "COURSE_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
