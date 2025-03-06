package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class InvalidLevelException extends BaseException {
    public InvalidLevelException(String level) {
        super("Unsupported level type: " + level, "INVALID_LEVEL_EXCEPTION", HttpStatus.BAD_REQUEST);
    }
}
