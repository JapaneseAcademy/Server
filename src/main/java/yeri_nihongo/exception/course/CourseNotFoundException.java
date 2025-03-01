package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class CourseNotFoundException extends BaseException {
    public CourseNotFoundException(Long courseId) {
        super("Course not found with courseId: " + courseId, "COURSE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
