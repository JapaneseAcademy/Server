package yeri_nihongo.exception.s3;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class S3AccessDeniedException extends BaseException {
    public S3AccessDeniedException() {
        super("Failed to authorize with S3", "S3_ACCESS_DENIED", HttpStatus.FORBIDDEN);
    }
}
