package yeri_nihongo.time.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TimeTableResponse {

    private Long timeTableId;
    private List<TimeBlockResponse> timeBlocks;
}
