package yeri_nihongo.exception.auth;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TokenMappingException extends BaseException {
    public TokenMappingException() {
        super("Token dto mapping error", "TOKEN_MAPPING_ERROR", HttpStatus.BAD_REQUEST);
    }
}
