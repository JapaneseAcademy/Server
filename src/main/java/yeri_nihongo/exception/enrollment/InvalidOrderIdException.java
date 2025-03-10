package yeri_nihongo.exception.enrollment;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class InvalidOrderIdException extends BaseException {
    public InvalidOrderIdException(String orderId) {
        super("Amount not found with order id: " + orderId, "INVALID_ORDER_ID_ERROR", HttpStatus.NOT_FOUND);
    }
}
