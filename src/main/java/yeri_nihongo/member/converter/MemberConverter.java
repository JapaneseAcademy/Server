package yeri_nihongo.member.converter;

import yeri_nihongo.exception.member.UserMappingException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.member.dto.response.MemberResponse;

public class MemberConverter {

    public static MemberResponse toMemberResponse(Member member) {
        try {
            return MemberResponse.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .phone(member.getPhone())
                    .birth(member.getBirth())
                    .build();
        } catch (Exception e) {
            throw new UserMappingException();
        }
    }
}
