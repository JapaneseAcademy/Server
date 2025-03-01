package yeri_nihongo.exception.member;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class UserMappingException extends BaseException {

    public UserMappingException() {
        super("User dto mapping error", "USER_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
