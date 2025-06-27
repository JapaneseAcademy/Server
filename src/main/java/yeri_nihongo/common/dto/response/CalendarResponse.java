package yeri_nihongo.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CalendarResponse {

    private Long instructorId;
    private String calendarUrl;
}
