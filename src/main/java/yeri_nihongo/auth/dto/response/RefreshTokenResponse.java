package yeri_nihongo.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.auth.dto.AuthToken;
import yeri_nihongo.exception.auth.TokenMappingException;

@Getter
@Builder
public class RefreshTokenResponse {

    private String accessToken;
    private String refreshToken;

    public static RefreshTokenResponse of(AuthToken token) {
        try {
            return RefreshTokenResponse.builder()
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .build();
        } catch (Exception e) {
            throw new TokenMappingException();
        }
    }
}
