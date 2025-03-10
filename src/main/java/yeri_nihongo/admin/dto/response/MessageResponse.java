package yeri_nihongo.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponse {

    private int result_code;
    private String message;
    private int error_cnt;
}
