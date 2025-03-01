package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class CourseInfoNotFoundException extends BaseException {
    public CourseInfoNotFoundException(Long courseInfoId) {
        super("CourseInfo not found with id: " + courseInfoId, "COURSE_INFO_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
