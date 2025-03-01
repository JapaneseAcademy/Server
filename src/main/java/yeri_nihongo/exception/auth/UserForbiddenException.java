package yeri_nihongo.exception.auth;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class UserForbiddenException extends BaseException {

    public UserForbiddenException(Long memberId) {
        super("Unavailable with member id: " + memberId, "USER_FORBIDDEN_ERROR", HttpStatus.FORBIDDEN);
    }
}
