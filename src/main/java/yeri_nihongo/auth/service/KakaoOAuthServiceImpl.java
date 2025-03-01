package yeri_nihongo.auth.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import yeri_nihongo.auth.dto.request.KakaoOAuthRequest;
import yeri_nihongo.auth.dto.response.KakaoOAuthTokenResponse;
import yeri_nihongo.auth.dto.response.LoginResponse;
import yeri_nihongo.config.auth.AuthTokenGenerator;
import yeri_nihongo.exception.auth.KakaoTokenResponseException;
import yeri_nihongo.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements KakaoOAuthService{

    private static final String provider = "kakao_";

    @Value("${oauth.kakao.rest-api-key}")
    private String restApiKey;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.kakao.client-secret}")
    private String clientSecret;

    private final MemberRepository memberRepository;
    private final AuthTokenGenerator authTokenGenerator;

    public LoginResponse kakaoLogin(KakaoOAuthRequest request) {
        // 1. 카카오 액세스 토큰 발급
        String accessToken = getAccessToken(request.getAuthorizationCode());

        // 2. 액세스 토큰을 이용해 카카오 id 정보 획득
        Long userId = getKakaoId(accessToken);

        // 3. 로그인(jwt 토큰 발급)
        return login(userId);
    }

    public String getAccessToken(String authorizationCode) {
        WebClient webClient = WebClient.builder().build();

        KakaoOAuthTokenResponse tokenResponse = webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", restApiKey)
                        .with("redirect_uri", redirectUri)
                        .with("code", authorizationCode)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(KakaoOAuthTokenResponse.class)
                .block();

        if (tokenResponse == null) {
            throw new KakaoTokenResponseException();
        }

        return tokenResponse.getAccessToken();
    }

    public Long getKakaoId(String accessToken) {
        KakaoUser kakaoUser = WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUser.class)
                .block();

        return kakaoUser.getId();
    }

    public LoginResponse login(Long userId) {
        String loginId = provider + userId;

        return memberRepository.findByLoginId(loginId)
                .map(member -> new LoginResponse(member, authTokenGenerator.generate(loginId, member.getRole()), false))
                .orElseGet(() -> new LoginResponse(loginId, true));
    }

    @Getter
    @Setter
    private static class KakaoUser {
        @JsonProperty("id")
        private Long id;
    }
}
