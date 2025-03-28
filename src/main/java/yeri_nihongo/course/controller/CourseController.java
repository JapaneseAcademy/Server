package yeri_nihongo.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeri_nihongo.course.dto.response.CourseInfoResponse;
import yeri_nihongo.course.dto.response.CourseListResponse;
import yeri_nihongo.course.dto.response.CourseTitleResponse;
import yeri_nihongo.course.service.CourseInfoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseInfoService courseInfoService;

    @GetMapping("/{courseInfoId}")
    public ResponseEntity<CourseInfoResponse> getCourseByCourseId(
            @PathVariable Long courseInfoId
    ) {
        CourseInfoResponse response = courseInfoService.getCourseInfoByCourseInfoId(courseInfoId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<CourseListResponse>> getAllCourses() {
        List<CourseListResponse> responses = courseInfoService.getAllCourseInfos();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/titles")
    public ResponseEntity<List<CourseTitleResponse>> getAllCourseTitles() {
        List<CourseTitleResponse> responses = courseInfoService.getAllCourseTitles();

        return ResponseEntity.ok(responses);
    }
}
