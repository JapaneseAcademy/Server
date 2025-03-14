package yeri_nihongo.exception.time;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TimeTableDeletionException extends BaseException {
    public TimeTableDeletionException(Long timeTableId) {
        super("The course(with id: " + timeTableId + ") cannot be deleted because there are registered students.",
                "TIME_TABLE_DELETION_ERROR",
                HttpStatus.CONFLICT);
    }
}
