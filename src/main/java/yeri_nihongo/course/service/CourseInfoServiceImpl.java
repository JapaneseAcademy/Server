package yeri_nihongo.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.course.converter.CourseConverter;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.domain.Level;
import yeri_nihongo.course.dto.response.*;
import yeri_nihongo.course.repository.CourseInfoRepository;
import yeri_nihongo.course.repository.DescriptionRepository;
import yeri_nihongo.exception.course.InvalidLevelException;

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
        CourseResponse course = courseService.getCurrentCourseByCourseInfoId(courseInfo.getId());

        return CourseConverter.toCourseInfoResponse(courseInfo, descriptionImageUrls, course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseListResponse> getAllCourseInfos() {
        List<CourseInfo> courseInfos = courseInfoRepository.findAll();

        return courseInfos.stream()
                .filter(courseInfo -> courseService.findSaleCostByCourseInfoId(courseInfo.getId()) != null)
                .map(courseInfo -> {
                    int saleCost = courseService.findSaleCostByCourseInfoId(courseInfo.getId());
                    return CourseConverter.toCourseListResponse(courseInfo, saleCost);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseTitleResponse> getAllCourseTitles() {
        List<CourseInfo> courseInfos = courseInfoRepository.findAll();

        return courseInfos.stream()
                .map(CourseConverter::toCourseTitleResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseListForAdminResponse> getAllCourseInfosForAdmin() {
        List<CourseInfo> courseInfos = courseInfoRepository.findAll();

        return courseInfos.stream()
                .map(courseInfo -> {
                    List<String> descriptions = descriptionRepository.getDescriptionImageUrlsByCourseInfoId(courseInfo.getId());
                    Integer saleCost = courseService.findSaleCostByCourseInfoId(courseInfo.getId());
                    return CourseConverter.toCourseListForAdminResponse(courseInfo, descriptions, saleCost != null ? saleCost : 0);
                }).toList();
    }

    private Level validateLevel(String level) {
        try {
            return Level.valueOf(level);
        } catch (Exception e) {
            throw new InvalidLevelException(level);
        }
    }
}
