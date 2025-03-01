package yeri_nihongo.exception.member;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(Object userId) {
        super("User not found with id: " + userId, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
