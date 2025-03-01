package yeri_nihongo.auth.service;

import yeri_nihongo.auth.dto.request.RefreshTokenRequest;
import yeri_nihongo.auth.dto.response.RefreshTokenResponse;

public interface AuthService {

    RefreshTokenResponse refresh(String header, RefreshTokenRequest request);

    String extractToken(String header);

    void logout();
}
