package yeri_nihongo.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.member.dto.request.MemberUpdateRequest;
import yeri_nihongo.member.dto.response.MemberResponse;
import yeri_nihongo.member.service.MemberService;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<MemberResponse> getProfile() {
        MemberResponse response = memberService.getProfile();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody @Valid MemberUpdateRequest request
            ) {
        memberService.updateMember(request);

        return ResponseEntity.ok().build();
    }
}
