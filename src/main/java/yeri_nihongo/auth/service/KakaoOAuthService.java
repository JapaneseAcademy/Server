package yeri_nihongo.auth.service;


import jakarta.servlet.http.HttpServletRequest;
import yeri_nihongo.auth.dto.request.KakaoOAuthRequest;
import yeri_nihongo.auth.dto.response.LoginResponse;

public interface KakaoOAuthService {

    LoginResponse kakaoLogin(KakaoOAuthRequest request, HttpServletRequest httpRequest);

    LoginResponse kakaoLoginForAdmin(KakaoOAuthRequest request, HttpServletRequest httpRequest);
}
