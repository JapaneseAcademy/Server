package yeri_nihongo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static AuthToken of(String grantType, String accessToken, String refreshToken) {
        return AuthToken.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
