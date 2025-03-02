package yeri_nihongo.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberForAdminResponse extends MemberResponse {

    private String notes;

    @Builder(builderMethodName = "adminBuilder")
    public MemberForAdminResponse(Long id, String name, String phone, LocalDate birth, String notes) {
        super(id, name, phone, birth);
        this.notes = notes;
    }
}
