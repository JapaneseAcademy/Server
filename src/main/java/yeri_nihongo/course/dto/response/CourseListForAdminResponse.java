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
            int baseCost,
            int saleCost,
            String mainImageUrl,
            boolean isLive,
            boolean isOnline,
            boolean isRecorded,
            Level level,
            List<String> descriptions
    ) {
        super(courseInfoId, title, baseCost, saleCost, mainImageUrl, isLive, isOnline, isRecorded, level, "");
        this.descriptions = descriptions;
    }
}
