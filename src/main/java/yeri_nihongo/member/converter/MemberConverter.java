package yeri_nihongo.member.converter;

import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.exception.member.UserMappingException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.member.dto.response.CourseStudentResponse;
import yeri_nihongo.member.dto.response.MemberForAdminResponse;
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

    public static MemberForAdminResponse toMemberForAdminResponse(Member member) {
        try {
            return MemberForAdminResponse.adminBuilder()
                    .id(member.getId())
                    .name(member.getName())
                    .phone(member.getPhone())
                    .birth(member.getBirth())
                    .note(member.getNote())
                    .build();
        } catch (Exception e) {
            throw new UserMappingException();
        }
    }

    public static CourseStudentResponse toCourseStudentResponse(Enrollment enrollment) {
        try {
            Member member = enrollment.getMember();

            return CourseStudentResponse.builder()
                    .name(member.getName())
                    .phone(member.getPhone())
                    .paymentDate(enrollment.getPaymentAt().toLocalDate())
                    .category(enrollment.getCategory())
                    .build();
        } catch (Exception e) {
            throw new UserMappingException();
        }
    }
}
