package yeri_nihongo.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import yeri_nihongo.exception.member.UserNotFoundException;
import yeri_nihongo.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new PrincipalDetails(memberRepository.findByLoginId(username).orElseThrow(
                () -> new UserNotFoundException(username)
        ));
    }

    public static Long getCurrentMemberId() {
        return getCurrentMember().getId();
    }

    public static String getCurrentLoginId() {
        return getCurrentMember().getLoginId();
    }

    private static PrincipalDetails getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof PrincipalDetails) {
            return (PrincipalDetails) principal;
        }

        throw new RuntimeException("유효하지 않은 사용자 정보입니다.");
    }
}
