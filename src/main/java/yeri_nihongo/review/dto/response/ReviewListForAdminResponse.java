package yeri_nihongo.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListForAdminResponse {

    private List<ReviewForAdminResponse> reviews;
    private Integer listSize;
    private Integer totalPage;
    private Long totalElements;
    private boolean isFirst;
    private boolean isLast;
}
