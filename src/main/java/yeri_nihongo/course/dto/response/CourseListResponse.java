package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseListResponse {

    private Long courseInfoId;
    private String title;
    private int cost;
    private String mainImageUrl;
    private boolean isLive;
    private boolean isOnline;
    private boolean isRecorded;
}
