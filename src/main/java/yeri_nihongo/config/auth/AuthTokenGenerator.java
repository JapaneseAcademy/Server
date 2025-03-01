package yeri_nihongo.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yeri_nihongo.auth.dto.AuthToken;
import yeri_nihongo.auth.service.RefreshTokenService;
import yeri_nihongo.config.jwt.JwtProvider;
import yeri_nihongo.member.domain.Role;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenGenerator {

    private static final String BEARER_TYPE = "Bearer";

    // access token: 1일, refresh token: 14일
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthToken generate(String loginId, Role role) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = jwtProvider.accessTokenGenerate(loginId, role, accessTokenExpiredAt);
        String refreshToken = jwtProvider.refreshTokenGenerate(refreshTokenExpiredAt);
        refreshTokenService.saveRefreshToken(loginId, role, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);

        return AuthToken.of(BEARER_TYPE, accessToken, refreshToken);
    }
}
