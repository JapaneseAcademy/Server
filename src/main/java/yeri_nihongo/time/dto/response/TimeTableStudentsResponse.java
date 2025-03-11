package yeri_nihongo.time.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TimeTableStudentsResponse extends TimeTableResponse {

    private String courseTitle;
    private List<Long> students;

    @Builder(builderMethodName = "timeTableStudentsBuilder")
    public TimeTableStudentsResponse(Long timeTableId, List<TimeBlockResponse> timeBlocks, String courseTitle, List<Long> students) {
        super(timeTableId, timeBlocks);
        this.courseTitle = courseTitle;
        this.students = students;
    }
}
