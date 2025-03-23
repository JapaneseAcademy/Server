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
import yeri_nihongo.enrollment.service.EnrollmentService;
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
    private final EnrollmentService enrollmentService;

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
                                int studentCount = enrollmentService.getCountByTimeTableId(timeTableResponse.getTimeTableId());
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
        Course course = courseRepository.findCourseByCourseInfoIdAndDate(request.getCourseInfoId(), request.getDate())
                .orElseGet(() -> {
                    System.out.println("new course created");
                    CourseInfo courseInfo = commonService.getCourseInfoByCourseInfoId(request.getCourseInfoId());
                    Course newCourse = CourseConverter.toEntity(request.getDate(), courseInfo);
                    return courseRepository.save(newCourse);
                });

        timeTableService.createTimeTable(course, request.getTimeBlocks());
    }

    @Override
    public Course getCurrentCourseEntityByCourseInfoId(Long courseInfoId) {
        return courseRepository.findCurrentCourseByCourseInfoId(courseInfoId)
                .orElseThrow(() -> new NoScheduledCourseException(courseInfoId));
    }

    @Override
    public boolean getExistsCurrentCourseByCourseInfoId(Long courseInfoId) {
        return courseRepository.existsCurrentCourseByCourseInfoId(courseInfoId);
    }
}
