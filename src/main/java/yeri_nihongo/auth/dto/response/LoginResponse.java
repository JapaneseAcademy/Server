package yeri_nihongo.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeri_nihongo.auth.dto.AuthToken;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.member.domain.Role;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String loginId;
    private String name;
    private Role role;
    private AuthToken token;
    private boolean requiresSignUp;

    public LoginResponse(Member member, AuthToken token, boolean requiresSignUp) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.role = member.getRole();
        this.token = token;
        this.requiresSignUp = requiresSignUp;
    }

    public LoginResponse(String loginId, boolean requiresSignUp) {
        this.loginId = loginId;
        this.requiresSignUp = requiresSignUp;
    }
}
