package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class NoScheduledCourseException extends BaseException {
    public NoScheduledCourseException(Long courseInfoId) {
        super("There is no schedule for courseInfo with id: " + courseInfoId, "NO_SCHEDULED_COURSE_ERROR", HttpStatus.NOT_FOUND);
    }
}
