package yeri_nihongo.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MessageRequest {

    @NotEmpty(message = "메시지를 수신할 수신자 목록은 필수 입력 사항입니다.")
    private List<Long> receiverIds;
    
    @NotBlank(message = "전송할 메시지 내용은 필수 입력 사항입니다.")
    private String message;
    
    @NotBlank(message = "메시지 테스트 여부는 필수 입력 사항입니다.")
    private String testMode;
}
