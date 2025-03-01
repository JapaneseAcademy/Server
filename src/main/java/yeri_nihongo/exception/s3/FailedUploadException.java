package yeri_nihongo.exception.s3;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class FailedUploadException extends BaseException {
    public FailedUploadException() {
        super("Wrong input or error with reading file", "FAILED_TO_UPLOAD_FILE", HttpStatus.BAD_REQUEST);
    }
}
