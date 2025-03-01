package yeri_nihongo.time.dto.response;

import lombok.Builder;
import lombok.Getter;
import yeri_nihongo.time.domain.Weekday;

import java.time.LocalTime;

@Getter
@Builder
public class TimeBlockResponse {

    private Weekday weekday;
    private LocalTime startTime;
    private LocalTime endTime;
}
