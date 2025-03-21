package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import yeri_nihongo.exception.enrollment.InvalidOrderIdException;
import yeri_nihongo.member.domain.Role;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;
    
    // 결제 승인 제한 시간 10분
    private static final long ORDER_EXPIRE_TIME = 1000 * 60 * 10;

    public void saveImageUrl(String key, String imageUrl) {
        redisTemplate.opsForValue().set(key, imageUrl);
    }

    public String getImageUrl(String key) {
        return redisTemplate.opsForValue().get(key);
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

    public void saveOrderIdAndAmount(String orderId, int amount) {
        redisTemplate.opsForValue().set(orderId, String.valueOf(amount), Duration.ofMillis(ORDER_EXPIRE_TIME));
    }

    public int getAmount(String orderId) {
        String amount = redisTemplate.opsForValue().get(orderId);

        if (amount == null) {
            throw new InvalidOrderIdException(orderId);
        }

        return Integer.parseInt(amount);
    }
}
