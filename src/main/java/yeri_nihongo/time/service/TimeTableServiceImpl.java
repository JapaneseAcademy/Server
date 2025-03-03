package yeri_nihongo.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeri_nihongo.time.converter.TimeConverter;
import yeri_nihongo.time.domain.TimeBlock;
import yeri_nihongo.time.domain.TimeTable;
import yeri_nihongo.time.dto.response.TimeBlockResponse;
import yeri_nihongo.time.dto.response.TimeTableResponse;
import yeri_nihongo.time.repository.TimeBlockRepository;
import yeri_nihongo.time.repository.TimeTableRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;
    private final TimeBlockRepository timeBlockRepository;

    @Override
    public List<TimeTableResponse> getTimeTablesByCourseId(Long courseId) {
        List<TimeTable> timeTables = timeTableRepository.findTimeTablesByCourseId(courseId);
        List<TimeTableResponse> timeTableResponses = timeTables.stream()
                .map(timeTable -> {
                    List<TimeBlock> timeBlocks = timeBlockRepository.findTimeBlocksByTimeTableId(timeTable.getId());
                    List<TimeBlockResponse> timeBlockResponses = timeBlocks.stream()
                            .map(TimeConverter::toTimeBlockResponse)
                            .toList();
                    return TimeConverter.toTimeTableResponse(timeTable, timeBlockResponses);
                })
                .toList();

        return timeTableResponses;
    }

    @Override
    public int getStudentCountByTimeTableId(Long timeTableId) {
        return timeTableRepository.findStudentCountByTimeTableId(timeTableId);
    }
}
