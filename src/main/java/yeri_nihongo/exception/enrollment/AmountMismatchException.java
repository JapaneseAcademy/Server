package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class AmountMismatchException extends BaseException {

    public AmountMismatchException() {
        super("Requested amount is not correct with saved amount", "AMOUNT_MISMATCH_ERROR", HttpStatus.BAD_REQUEST);
    }
}
