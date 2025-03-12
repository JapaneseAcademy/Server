package yeri_nihongo.time.converter;

import yeri_nihongo.course.domain.Course;
import yeri_nihongo.exception.course.CourseMappingException;
import yeri_nihongo.exception.time.TimeTableMappingException;
import yeri_nihongo.time.domain.TimeBlock;
import yeri_nihongo.time.domain.TimeTable;
import yeri_nihongo.time.dto.request.TimeBlockCreateRequest;
import yeri_nihongo.time.dto.response.TimeBlockResponse;
import yeri_nihongo.time.dto.response.TimeTableResponse;
import yeri_nihongo.time.dto.response.TimeTableStudentsResponse;

import java.util.List;

public class TimeConverter {

    public static TimeTable toTimeTableEntity(Course course) {
        try {
            return TimeTable.builder()
                    .course(course)
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    public static TimeBlock toTimeBlockEntity(TimeTable timeTable, TimeBlockCreateRequest request) {
        try {
            return TimeBlock.builder()
                    .timeTable(timeTable)
                    .weekday(request.getWeekday())
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .build();
        } catch (Exception e) {
            throw new TimeTableMappingException();
        }
    }

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

    public  static TimeTableStudentsResponse toTimeTableStudentsResponse(
            TimeTable timeTable, List<TimeBlock> timeBlocks, String courseTitle, List<Long> students
    ) {
        try {
            List<TimeBlockResponse> timeBlockResponses = timeBlocks.stream()
                    .map(TimeConverter::toTimeBlockResponse)
                    .toList();

            return TimeTableStudentsResponse.timeTableStudentsBuilder()
                    .timeTableId(timeTable.getId())
                    .courseTitle(courseTitle)
                    .timeBlocks(timeBlockResponses)
                    .students(students)
                    .build();
        } catch (Exception e) {
            throw new TimeTableMappingException();
        }
    }
}
