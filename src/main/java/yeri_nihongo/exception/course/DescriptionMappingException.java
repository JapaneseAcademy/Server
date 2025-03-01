package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class DescriptionMappingException extends BaseException {
    public DescriptionMappingException() {
        super("Description dto mapping error", "DESCRIPTION_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
