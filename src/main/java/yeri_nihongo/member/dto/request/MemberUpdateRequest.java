package yeri_nihongo.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberUpdateRequest {

    @NotBlank(message = "사용자 이름은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "사용자 전화번호는 필수 입력 사항입니다.")
    @Pattern(
            regexp = "^(010)\\d{8}$",
            message = "올바른 전화번호 형식을 입력해주세요.(ex: 01012345678)"
    )
    private String phone;

    @NotNull(message = "사용자 생년월일을 필수 입력 사항입니다.")
    @Past(message = "생년월일은 과거 날짜만 가능합니다.")
    private LocalDate birth;
}
