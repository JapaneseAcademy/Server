package yeri_nihongo.exception.time;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TimeTableNotFoundException extends BaseException {
    public TimeTableNotFoundException(Long timeTableId) {
        super("TimeTable not found with id: " + timeTableId, "TIMETABLE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
