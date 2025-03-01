package yeri_nihongo.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JoinRequest {

    @NotBlank(message = "로그인 id는 필수 입력 사항입니다.")
    private String loginId;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    @Size(max = 10, message = "이름은 20자 이내로 입력해주세요.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
    @Pattern(
            regexp = "^(010)\\d{8}$",
            message = "올바른 전화번호 형식을 입력해주세요.(ex: 01012345678)"
    )
    private String phone;

    @Past(message = "생년월일은 과거 날짜만 가능합니다.")
    private LocalDate birth;
}
