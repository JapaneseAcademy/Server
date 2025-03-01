package yeri_nihongo.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import yeri_nihongo.member.domain.Role;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;

    private String getKey(String id) {
        return "refreshToken:" + id;
    }

    // Refresh Token 저장
    public void saveRefreshToken(String id, Role role, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(getKey(id), role + ":" + refreshToken, Duration.ofMillis(duration));
    }

    // Refresh Token 조회
    public String getRefreshToken(String id) {
        return redisTemplate.opsForValue().get(getKey(id));
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(String id) {
        redisTemplate.delete(getKey(id));
    }
}
