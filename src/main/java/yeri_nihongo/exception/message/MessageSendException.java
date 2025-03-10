package yeri_nihongo.exception.message;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class MessageSendException extends BaseException {
    public MessageSendException(String message) {
        super(message, "MESSAGE_SEND_ERROR", HttpStatus.BAD_REQUEST);
    }
}
