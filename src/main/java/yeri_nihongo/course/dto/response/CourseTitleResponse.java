package yeri_nihongo.course.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseTitleResponse {

    private Long courseInfoId;
    private String title;
}
