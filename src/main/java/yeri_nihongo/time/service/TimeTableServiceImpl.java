package yeri_nihongo.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.dto.request.CostUpdateRequest;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.repository.EnrollmentRepository;
import yeri_nihongo.exception.time.TimeTableDeletionException;
import yeri_nihongo.time.converter.TimeConverter;
import yeri_nihongo.time.domain.TimeBlock;
import yeri_nihongo.time.domain.TimeTable;
import yeri_nihongo.time.dto.request.TimeBlockCreateRequest;
import yeri_nihongo.time.dto.response.TimeBlockResponse;
import yeri_nihongo.time.dto.response.TimeTableResponse;
import yeri_nihongo.time.dto.response.TimeTableStudentsResponse;
import yeri_nihongo.time.repository.TimeBlockRepository;
import yeri_nihongo.time.repository.TimeTableRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private final CommonService commonService;

    private final TimeTableRepository timeTableRepository;
    private final TimeBlockRepository timeBlockRepository;
    private final EnrollmentRepository enrollmentRepository;

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
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByTimeTableId(Long timeTableId) {
        return timeTableRepository.findEnrollmentsByTimeTableId(timeTableId);
    }

    @Override
    public int getSaleCostByTimeTableId(Long timeTableId) {
        return timeTableRepository.findSaleCostByTimeTableId(timeTableId);
    }

    @Override
    public List<TimeTableStudentsResponse> getCourseStudents(Course course) {
        List<TimeTable> timeTables = timeTableRepository.findTimeTablesByCourseId(course.getId());
        return timeTables.stream()
                .map(timeTable -> {
                    List<TimeBlock> timeBlocks = timeBlockRepository.findTimeBlocksByTimeTableId(timeTable.getId());
                    List<Long> students = timeTableRepository.findMemberIdsByTimeTableId(timeTable.getId());
                    String courseTitle = commonService.getCourseTitleByCourseId(course.getId());
                    return TimeConverter.toTimeTableStudentsResponse(timeTable, timeBlocks, courseTitle, students);
                })
                .toList();
    }

    @Override
    public void createTimeTable(Course course, List<TimeBlockCreateRequest> requests) {
        TimeTable timeTable = timeTableRepository.save(TimeConverter.toTimeTableEntity(course));

        List<TimeBlock> timeBlocks = requests.stream()
                .map(request -> TimeConverter.toTimeBlockEntity(timeTable, request))
                .toList();

        timeBlockRepository.saveAll(timeBlocks);
    }

    @Override
    @Transactional
    public void deleteTimeTable(Long timeTableId) {
        int studentCount = enrollmentRepository.findCountByTimeTableId(timeTableId);

        if (studentCount == 0) {
            TimeTable timeTable = commonService.getTimeTableByTimeTableId(timeTableId);
            timeTableRepository.delete(timeTable);
        } else {
            throw new TimeTableDeletionException(timeTableId);
        }
    }

    @Override
    @Transactional
    public void updateSaleCostByTimeTableId(Long timeTableId, CostUpdateRequest request) {
        Course course = timeTableRepository.findCourseByTimeTableId(timeTableId);
        course.updateCost(request.getCost());
    }
}
