package yeri_nihongo.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.auth.dto.AuthToken;
import yeri_nihongo.auth.dto.request.JoinRequest;
import yeri_nihongo.auth.dto.response.LoginResponse;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.config.auth.AuthTokenGenerator;
import yeri_nihongo.config.auth.PrincipalDetailsService;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.exception.member.UserAlreadyExistsException;
import yeri_nihongo.exception.member.UserNotFoundException;
import yeri_nihongo.member.converter.MemberConverter;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.member.domain.Role;
import yeri_nihongo.member.dto.request.MemberNoteRequest;
import yeri_nihongo.member.dto.request.MemberUpdateRequest;
import yeri_nihongo.member.dto.response.CourseStudentResponse;
import yeri_nihongo.member.dto.response.MemberForAdminResponse;
import yeri_nihongo.member.dto.response.MemberResponse;
import yeri_nihongo.member.repository.MemberRepository;
import yeri_nihongo.time.service.TimeTableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final CommonService commonService;
    private final TimeTableService timeTableService;

    private final MemberRepository memberRepository;
    private final AuthTokenGenerator authTokenGenerator;

    @Override
    @Transactional
    public LoginResponse joinMember(JoinRequest request) {
        validateMemberDoesNotExist(request.getLoginId());

        Member member = Member.builder()
                .loginId(request.getLoginId())
                .name(request.getName())
                .phone(request.getPhone())
                .birth(request.getBirth())
                .role(Role.STUDENT)
                .build();

        memberRepository.save(member);
        AuthToken token = authTokenGenerator.generate(member.getLoginId(), member.getRole());

        return new LoginResponse(member, token, false);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getProfile() {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));

        return MemberConverter.toMemberResponse(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberForAdminResponse> getAllStudents() {
        List<Member> members = memberRepository.findAllStudent();

        return members.stream()
                .map(MemberConverter::toMemberForAdminResponse)
                .toList();
    }

    @Override
    @Transactional
    public void updateMember(MemberUpdateRequest request) {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        Member member = commonService.getMemberByMemberId(memberId);
        member.updateMember(request.getName(), request.getPhone(), request.getBirth());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseStudentResponse> getStudentsByTimeTableId(Long timeTableId) {
        List<Enrollment> enrollments = timeTableService.getEnrollmentsByTimeTableId(timeTableId);

        return enrollments.stream()
                .map(MemberConverter::toCourseStudentResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public String getPhoneByMemberId(Long memberId) {
        return memberRepository.findPhoneByMemberId(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));
    }

    @Override
    @Transactional
    public void updateMemberNote(Long memberId, MemberNoteRequest request) {
        Member member = commonService.getMemberByMemberId(memberId);
        member.updateNote(request.getNote());
    }

    private void validateMemberDoesNotExist(String loginId) {
        if (memberRepository.findByLoginId(loginId).isPresent()) {
            throw new UserAlreadyExistsException(loginId);
        }
    }
}
