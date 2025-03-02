package yeri_nihongo.member.service;

import yeri_nihongo.auth.dto.request.JoinRequest;
import yeri_nihongo.auth.dto.response.LoginResponse;
import yeri_nihongo.member.dto.response.MemberForAdminResponse;
import yeri_nihongo.member.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {

    LoginResponse joinMember(JoinRequest request);

    MemberResponse getProfile();

    List<MemberForAdminResponse> getAllStudents();
}
