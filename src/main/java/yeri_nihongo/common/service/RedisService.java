package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import yeri_nihongo.member.domain.Role;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;
    private static final String YOUTUBE_KEY = "YoutubeUrl";

    public void saveYoutubeUrl(String youtubeUrl) {
        redisTemplate.opsForValue().set(YOUTUBE_KEY, youtubeUrl);
    }

    public String getYoutubeUrl() {
        return redisTemplate.opsForValue().get(YOUTUBE_KEY);
    }

    private String getRefreshTokenKey(String id) {
        return "refreshToken:" + id;
    }

    // Refresh Token 저장
    public void saveRefreshToken(String id, Role role, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(getRefreshTokenKey(id), role + ":" + refreshToken, Duration.ofMillis(duration));
    }

    // Refresh Token 조회
    public String getRefreshToken(String id) {
        return redisTemplate.opsForValue().get(getRefreshTokenKey(id));
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(String id) {
        redisTemplate.delete(getRefreshTokenKey(id));
    }
}
