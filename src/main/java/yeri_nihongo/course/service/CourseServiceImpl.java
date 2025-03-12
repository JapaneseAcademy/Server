package yeri_nihongo.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.course.converter.CourseConverter;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.dto.request.CourseCreateRequest;
import yeri_nihongo.course.dto.response.CourseForAdminResponse;
import yeri_nihongo.course.dto.response.CourseResponse;
import yeri_nihongo.course.repository.CourseInfoRepository;
import yeri_nihongo.course.repository.CourseRepository;
import yeri_nihongo.exception.course.NoScheduledCourseException;
import yeri_nihongo.time.dto.response.TimeTableResponse;
import yeri_nihongo.time.dto.response.TimeTableStudentsResponse;
import yeri_nihongo.time.service.TimeTableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CommonService commonService;
    private final TimeTableService timeTableService;

    private final CourseRepository courseRepository;
    private final CourseInfoRepository courseInfoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CourseForAdminResponse> getCoursesForAdmin(String date) {
        List<Course> courses = courseRepository.searchWithFilter(date);
        List<CourseForAdminResponse> responses = courses.stream()
                .flatMap(course -> {
                    String title = commonService.getCourseTitleByCourseId(course.getId());
                    List<TimeTableResponse> timeTableResponses = timeTableService.getTimeTablesByCourseId(course.getId());
                    return timeTableResponses.stream()
                            .map(timeTableResponse ->{
                                int studentCount = timeTableService.getStudentCountByTimeTableId(timeTableResponse.getTimeTableId());
                                Integer baseCost = courseRepository.findBaseCostByCourseId(course.getId())
                                        .orElse(0);
                                return CourseConverter.toCourseForAdminResponse(course, timeTableResponse, studentCount, title, baseCost);
                                    });
                })
                .toList();

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCurrentCourseByCourseInfoId(Long courseInfoId) {
        Course course = courseRepository.findCurrentCourseByCourseInfoId(courseInfoId)
                .orElseThrow(() -> new NoScheduledCourseException(courseInfoId));
        List<TimeTableResponse> timeTableResponses = timeTableService.getTimeTablesByCourseId(course.getId());
        int baseCost = courseInfoRepository.findCostByCourseInfoId(courseInfoId);

        return CourseConverter.toCourseResponse(course, timeTableResponses, baseCost);
    }

    @Override
    public Integer findSaleCostByCourseInfoId(Long courseInfoId) {
        return courseRepository.findSaleCostByCourseInfoId(courseInfoId)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeTableStudentsResponse> getTimeTableStudentsList(String filter) {
        List<Course> courses = courseRepository.searchWithFilter(filter);
        return courses.stream()
                .flatMap(course -> timeTableService.getCourseStudents(course).stream())
                .toList();
    }

    @Override
    @Transactional
    public void createCourse(CourseCreateRequest request) {
        CourseInfo courseInfo = commonService.getCourseInfoByCourseInfoId(request.getCourseInfoId());
        Course course = CourseConverter.toEntity(request.getDate(), courseInfo, request.getCost());
        courseRepository.save(course);

        timeTableService.createTimeTable(course, request.getTimeBlocks());
    }
}
