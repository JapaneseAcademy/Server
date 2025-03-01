package yeri_nihongo.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.course.converter.CourseConverter;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.dto.response.CourseInfoResponse;
import yeri_nihongo.course.dto.response.CourseListResponse;
import yeri_nihongo.course.dto.response.CourseResponse;
import yeri_nihongo.course.repository.CourseInfoRepository;
import yeri_nihongo.course.repository.DescriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseInfoServiceImpl implements CourseInfoService {

    private final CommonService commonService;
    private final CourseService courseService;

    private final CourseInfoRepository courseInfoRepository;
    private final DescriptionRepository descriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public CourseInfoResponse getCourseInfoByCourseInfoId(Long courseInfoId) {
        CourseInfo courseInfo = commonService.getCourseInfoByCourseInfoId(courseInfoId);
        List<String> descriptionImageUrls = descriptionRepository.getDescriptionImageUrlsByCourseInfoId(courseInfo.getId());
        List<CourseResponse> courses = courseService.getCoursesByCourseInfoId(courseInfo.getId());

        return CourseConverter.toCourseInfoResponse(courseInfo, descriptionImageUrls, courses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseListResponse> getAllCourses() {
        List<CourseInfo> courseInfos = courseInfoRepository.findAll();

        return courseInfos.stream()
                .map(CourseConverter::toCourseListResponse)
                .toList();
    }
}
