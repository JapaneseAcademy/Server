package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.course.domain.Level;

import java.util.List;

@Getter
public class CourseListForAdminResponse extends CourseListResponse{

    List<String> descriptions;

    @Builder(builderMethodName = "adminBuilder")
    CourseListForAdminResponse(
            Long courseInfoId,
            String title,
            int cost,
            String mainImageUrl,
            boolean isLive,
            boolean isOnline,
            boolean isRecorded,
            Level level,
            List<String> descriptions
    ) {
        super(courseInfoId, title, cost, mainImageUrl, isLive, isOnline, isRecorded, level);
        this.descriptions = descriptions;
    }
}
