package yeri_nihongo.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeri_nihongo.member.dto.response.MemberForAdminResponse;
import yeri_nihongo.member.service.MemberService;
import yeri_nihongo.review.dto.response.ReviewForAdminResponse;
import yeri_nihongo.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final ReviewService reviewService;

    @PostMapping("/token")
    public ResponseEntity<?> isInstructor() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    public ResponseEntity<List<MemberForAdminResponse>> getAllStudents() {
        List<MemberForAdminResponse> responses = memberService.getAllStudents();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewForAdminResponse>> getReviewsForAdmin() {
        List<ReviewForAdminResponse> responses = reviewService.getReviewsForAdmin();

        return ResponseEntity.ok(responses);
    }
}
