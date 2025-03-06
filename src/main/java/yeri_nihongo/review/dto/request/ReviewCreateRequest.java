package yeri_nihongo.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {

    @NotNull(message = "수강 내역 id는 필수 입력 정보입니다.")
    private Long enrollmentId;

    @NotBlank(message = "리뷰 제목은 필수 입력 정보입니다.")
    @Size(max = 30, message = "리뷰 제목은 최대 30자 이하입니다.")
    private String title;

    @NotBlank(message = "리뷰 내용은 필수 입력 정보입니다.")
    @Size(min = 10, max = 1000, message = "리뷰 내용은 최소 10자 이상, 최대 1000자 이하입니다.")
    private String review;

    @NotNull(message = "리뷰 작성자 익명 여부는 필수 입력 정보입니다.")
    private Boolean isAnonymous;
}
