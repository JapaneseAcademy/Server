package yeri_nihongo.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageRequest {

    private String receiver;
    private String message;
    private String testMode;
}
