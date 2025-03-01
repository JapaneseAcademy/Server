package yeri_nihongo.exception.auth;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class KakaoTokenResponseException extends BaseException {
    public KakaoTokenResponseException() {
        super("Kakao information with token failed", "KAKAO_TOKEN_ERROR", HttpStatus.BAD_REQUEST);
    }
}
