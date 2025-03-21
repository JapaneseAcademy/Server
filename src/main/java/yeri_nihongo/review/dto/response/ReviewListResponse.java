package yeri_nihongo.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListResponse {

    private List<ReviewDetailResponse> reviews;
    private Integer listSize;
    private Integer totalPage;
    private Long totalElements;
    private boolean isFirst;
    private boolean isLast;
}
