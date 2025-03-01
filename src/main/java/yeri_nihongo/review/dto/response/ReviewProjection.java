package yeri_nihongo.review.dto.response;

import java.time.LocalDateTime;

public interface ReviewProjection {

    Long getId();
    String getTitle();
    String getReview();
    LocalDateTime getCreatedAt();
}
