package yeri_nihongo.course.converter;

import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.dto.response.*;
import yeri_nihongo.exception.course.CourseMappingException;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.util.List;

public class CourseConverter {

    public static CourseInfoResponse toCourseInfoResponse(
            CourseInfo courseInfo, List<String> descriptions, CourseResponse course
    ) {
        try {
            return CourseInfoResponse.builder()
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .mainImageUrl(courseInfo.getMainImageUrl())
                    .isLive(courseInfo.getIsLive())
                    .isOnline(courseInfo.getIsOnline())
                    .isRecorded(courseInfo.getIsRecorded())
                    .descriptions(descriptions)
                    .level(courseInfo.getLevel())
                    .course(course)
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    public static CourseResponse toCourseResponse(
            Course course, List<TimeTableResponse> timeTables, int baseCost
    ) {
        try {
            return CourseResponse.builder()
                    .courseId(course.getId())
                    .startDate(course.getStartDate())
                    .endDate(course.getEndDate())
                    .baseCost(baseCost)
                    .saleCost(course.getCost())
                    .timeTables(timeTables)
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    public static CourseListResponse toCourseListResponse(
            CourseInfo courseInfo, int saleCost
    ) {
        try {
            return CourseListResponse.builder()
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .baseCost(courseInfo.getCost())
                    .saleCost(saleCost)
                    .isLive(courseInfo.getIsLive())
                    .isOnline(courseInfo.getIsOnline())
                    .isRecorded(courseInfo.getIsRecorded())
                    .mainImageUrl(courseInfo.getMainImageUrl())
                    .level(courseInfo.getLevel())
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    public static CourseForAdminResponse toCourseForAdminResponse(
            Course course, TimeTableResponse timeTable, int studentCount, String title, int baseCost
    ) {
        try {
            return CourseForAdminResponse.builder()
                    .courseId(course.getId())
                    .startDate(course.getStartDate())
                    .endDate(course.getEndDate())
                    .baseCost(baseCost)
                    .saleCost(course.getCost())
                    .title(title)
                    .studentCount(studentCount)
                    .timeTable(timeTable)
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    public static CourseTitleResponse toCourseTitleResponse(CourseInfo courseInfo) {
        try {
            return CourseTitleResponse.builder()
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    public static CourseListForAdminResponse toCourseListForAdminResponse(
            CourseInfo courseInfo, List<String> descriptions, Integer saleCost
    ) {
        try {
            return CourseListForAdminResponse.adminBuilder()
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .baseCost(courseInfo.getCost())
                    .saleCost(saleCost)
                    .isLive(courseInfo.getIsLive())
                    .isOnline(courseInfo.getIsOnline())
                    .isRecorded(courseInfo.getIsRecorded())
                    .mainImageUrl(courseInfo.getMainImageUrl())
                    .descriptions(descriptions)
                    .level(courseInfo.getLevel())
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }
}
