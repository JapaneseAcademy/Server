package yeri_nihongo.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoOAuthRequest {

    @NotBlank(message = "인가 코드는 필수 입력 사항입니다.")
    private String authorizationCode;
}