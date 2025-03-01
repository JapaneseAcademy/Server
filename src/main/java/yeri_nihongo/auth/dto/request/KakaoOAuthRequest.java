package yeri_nihongo.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoOAuthRequest {
    private String authorizationCode;
}