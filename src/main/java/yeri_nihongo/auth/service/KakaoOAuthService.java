package yeri_nihongo.auth.service;


import yeri_nihongo.auth.dto.request.KakaoOAuthRequest;
import yeri_nihongo.auth.dto.response.LoginResponse;

public interface KakaoOAuthService {

    LoginResponse kakaoLogin(KakaoOAuthRequest request);

    String getAccessToken(String authorizationCode);

    Long getKakaoId(String accessToken);
}
