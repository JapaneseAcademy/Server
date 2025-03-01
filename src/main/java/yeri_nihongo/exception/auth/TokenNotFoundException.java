package yeri_nihongo.exception.auth;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException() {
        super("Token is required", "EMPTY_TOKEN_ERROR", HttpStatus.UNAUTHORIZED);
    }
}
