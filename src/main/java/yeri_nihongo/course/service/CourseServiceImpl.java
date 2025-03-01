package yeri_nihongo.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeri_nihongo.course.converter.CourseConverter;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.dto.response.CourseResponse;
import yeri_nihongo.course.repository.CourseRepository;
import yeri_nihongo.time.dto.response.TimeTableResponse;
import yeri_nihongo.time.service.TimeTableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final TimeTableService timeTableService;

    private final CourseRepository courseRepository;

    @Override
    public List<CourseResponse> getCoursesByCourseInfoId(Long courseInfoId) {
        List<Course> courses = courseRepository.findCoursesByCourseInfoId(courseInfoId);
        List<CourseResponse> courseResponses = courses.stream()
                .map(course -> {
                    List<TimeTableResponse> timeTables = timeTableService.getTimeTablesByCourseId(course.getId());
                    return CourseConverter.toCourseResponse(course, timeTables);
                })
                .toList();
        return courseResponses;
    }
}
