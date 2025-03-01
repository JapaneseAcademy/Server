package yeri_nihongo.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import yeri_nihongo.config.auth.PrincipalDetails;
import yeri_nihongo.config.auth.PrincipalDetailsService;
import yeri_nihongo.member.domain.Role;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private final PrincipalDetailsService principalDetailsService;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, PrincipalDetailsService principalDetailsService) {
        this.principalDetailsService = principalDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String loginId = getLoginIdFromAccessToken(token);
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(loginId);

        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    public String accessTokenGenerate(String loginId, Role role, Date expiredAt) {
        return Jwts.builder()
                .setSubject(loginId)
                .claim("role", role)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshTokenGenerate(Date expiredAt) {
        return Jwts.builder()
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getLoginIdFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            // 토큰의 유효성 검사를 위해 파싱 시도
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // 파싱이 성공하면 토큰은 유효함
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false; // 위 예외 중 하나라도 발생하면 토큰은 유효하지 않음
    }
}
