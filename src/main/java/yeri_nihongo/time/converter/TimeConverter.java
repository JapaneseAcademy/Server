package yeri_nihongo.time.converter;

import yeri_nihongo.exception.time.TimeTableMappingException;
import yeri_nihongo.time.domain.TimeBlock;
import yeri_nihongo.time.domain.TimeTable;
import yeri_nihongo.time.dto.response.TimeBlockResponse;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.util.List;

public class TimeConverter {

    public static TimeBlockResponse toTimeBlockResponse(TimeBlock timeBlock) {
        try {
            return TimeBlockResponse.builder()
                    .weekday(timeBlock.getWeekday())
                    .startTime(timeBlock.getStartTime())
                    .endTime(timeBlock.getEndTime())
                    .build();
        } catch (Exception e) {
            throw new TimeTableMappingException();
        }
    }

    public static TimeTableResponse toTimeTableResponse(TimeTable timeTable, List<TimeBlockResponse> timeBlocks) {
        try {
            return TimeTableResponse.builder()
                    .timeTableId(timeTable.getId())
                    .timeBlocks(timeBlocks)
                    .build();
        } catch (Exception e) {
            throw new TimeTableMappingException();
        }
    }
}
