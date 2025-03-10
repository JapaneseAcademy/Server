package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class TossConfirmFailedException extends BaseException {
    public TossConfirmFailedException(String message, String code) {
        super(message, code, HttpStatus.BAD_REQUEST);
    }
}
