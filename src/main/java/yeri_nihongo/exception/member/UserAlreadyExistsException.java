package yeri_nihongo.exception.member;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String loginId) {
        super("User already exists with login ID " + loginId, "USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
    }
}
