package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.course.domain.Level;

import java.util.List;

@Getter
@Builder
public class CourseInfoResponse {

    private Long courseInfoId;
    private String title;
    private String mainImageUrl;
    private List<String> descriptions;
    private boolean isLive;
    private boolean isOnline;
    private boolean isRecorded;
    private boolean isLiveOnline;
    private Level level;
    private CourseResponse course;
    private String calendarUrl;
}
