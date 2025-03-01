package yeri_nihongo.exception.course;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TagMappingException extends BaseException {
    public TagMappingException() {
        super("Tag dto mapping error", "TAG_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
