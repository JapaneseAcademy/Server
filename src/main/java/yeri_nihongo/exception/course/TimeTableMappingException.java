package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TimeTableMappingException extends BaseException {
    public TimeTableMappingException() {
        super("TimeBlock dto mapping error", "TIMETABLE_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
