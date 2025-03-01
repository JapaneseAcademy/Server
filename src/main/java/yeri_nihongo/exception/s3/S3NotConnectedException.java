package yeri_nihongo.exception.s3;

import org.springframework.http.HttpStatus;
import yeri_nihongo.exception.common.BaseException;

public class S3NotConnectedException extends BaseException {
    public S3NotConnectedException() {
        super("Failed to connect S3", "S3_NOT_CONNECTED", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
