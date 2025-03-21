package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.course.domain.Level;

@Getter
@Builder
public class CourseListResponse {

    private Long courseInfoId;
    private String title;
    private int baseCost;
    private int saleCost;
    private String mainImageUrl;
    private boolean isLive;
    private boolean isOnline;
    private boolean isRecorded;
    private Level level;
    private String date;
}
