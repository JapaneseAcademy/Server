package yeri_nihongo.exception.auth;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class InvalidRefreshTokenException extends BaseException {
    public InvalidRefreshTokenException() {
        super("Refresh token expired or invalid", "INVALID_REFRESH_TOKEN", HttpStatus.UNAUTHORIZED);
    }
}
