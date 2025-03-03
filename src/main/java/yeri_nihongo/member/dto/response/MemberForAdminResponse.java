package yeri_nihongo.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberForAdminResponse extends MemberResponse {

    private String note;

    @Builder(builderMethodName = "adminBuilder")
    public MemberForAdminResponse(Long id, String name, String phone, LocalDate birth, String note) {
        super(id, name, phone, birth);
        this.note = note;
    }
}
