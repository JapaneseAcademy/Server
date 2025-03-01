package yeri_nihongo.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.auth.dto.request.JoinRequest;
import yeri_nihongo.auth.dto.request.KakaoOAuthRequest;
import yeri_nihongo.auth.dto.request.RefreshTokenRequest;
import yeri_nihongo.auth.dto.response.LoginResponse;
import yeri_nihongo.auth.dto.response.RefreshTokenResponse;
import yeri_nihongo.auth.service.AuthService;
import yeri_nihongo.auth.service.KakaoOAuthService;
import yeri_nihongo.member.service.MemberService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final KakaoOAuthService kakaoOAuthService;
    private final AuthService authService;

    @PostMapping("/members")
    public ResponseEntity<LoginResponse> joinMember(@Valid @RequestBody JoinRequest request) {
        LoginResponse loginResponse = memberService.joinMember(request);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody KakaoOAuthRequest request) {
        LoginResponse loginResponse = kakaoOAuthService.kakaoLogin(request);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response = authService.refresh(authorizationHeader, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<?> isInstructor() {
        return ResponseEntity.ok().build();
    }
}
