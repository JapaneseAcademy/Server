package yeri_nihongo.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberResponse {

    private Long id;
    private String name;
    private String phone;
    private LocalDate birth;
}
