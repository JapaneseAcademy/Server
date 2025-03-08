package yeri_nihongo.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.auth.dto.AuthToken;
import yeri_nihongo.auth.dto.request.RefreshTokenRequest;
import yeri_nihongo.auth.dto.response.RefreshTokenResponse;
import yeri_nihongo.common.service.RedisService;
import yeri_nihongo.config.auth.AuthTokenGenerator;
import yeri_nihongo.config.auth.PrincipalDetailsService;
import yeri_nihongo.config.jwt.JwtProvider;
import yeri_nihongo.exception.auth.InvalidRefreshTokenException;
import yeri_nihongo.exception.auth.TokenNotFoundException;
import yeri_nihongo.member.domain.Role;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final RedisService redisService;

    private final JwtProvider jwtProvider;
    private final AuthTokenGenerator authTokenGenerator;

    @Override
    @Transactional
    public RefreshTokenResponse refresh(String header, RefreshTokenRequest request) {
        // refresh token 있는지 확인
        String inputToken = extractToken(header);
        String loginId = request.getLoginId();

        // refresh token 유효성 확인
        String role = validateRefreshToken(inputToken, loginId);

        // 새로운 access token, refresh token 발급
        AuthToken token = authTokenGenerator.generate(loginId, Role.valueOf(role));

        return RefreshTokenResponse.of(token);
    }

    private String validateRefreshToken(String refreshToken, String loginId) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        String storedToken = redisService.getRefreshToken(loginId);
        if (storedToken == null || !refreshToken.equals(storedToken.split(":")[1])) {
            throw new InvalidRefreshTokenException();
        }

        return storedToken.split(":")[0];
    }

    @Override
    public String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new TokenNotFoundException();
        }

        return header.substring(7);
    }

    @Override
    @Transactional
    public void logout() {
        String loginId = PrincipalDetailsService.getCurrentLoginId();
        redisService.deleteRefreshToken(loginId);
    }
}
