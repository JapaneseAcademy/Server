package yeri_nihongo.course.converter;

import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.course.dto.response.*;
import yeri_nihongo.exception.course.CourseMappingException;
import yeri_nihongo.time.dto.response.TimeTableResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CourseConverter {

    public static Course toEntity(
            String date, CourseInfo courseInfo
    ) {
        try {
            String year = date.split("-")[0];
            String month = date.split("-")[1];

            YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));

            return Course.builder()
                    .courseInfo(courseInfo)
                    .cost(courseInfo.getCost())
                    .startDate(yearMonth.atDay(1))
                    .endDate(yearMonth.atEndOfMonth())
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

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
            CourseInfo courseInfo, Course course
    ) {
        try {
            return CourseListResponse.builder()
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .baseCost(courseInfo.getCost())
                    .saleCost(course.getCost())
                    .isLive(courseInfo.getIsLive())
                    .isOnline(courseInfo.getIsOnline())
                    .isRecorded(courseInfo.getIsRecorded())
                    .isLiveOnline(courseInfo.getIsLiveOnline())
                    .mainImageUrl(courseInfo.getMainImageUrl())
                    .level(courseInfo.getLevel())
                    .date(getDate(course))
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
            CourseInfo courseInfo, List<String> descriptions, Course course
    ) {
        try {
            int cost = course == null ? 0 : course.getCost();

            return CourseListForAdminResponse.adminBuilder()
                    .courseInfoId(courseInfo.getId())
                    .title(courseInfo.getTitle())
                    .baseCost(courseInfo.getCost())
                    .saleCost(cost)
                    .isLive(courseInfo.getIsLive())
                    .isOnline(courseInfo.getIsOnline())
                    .isRecorded(courseInfo.getIsRecorded())
                    .isRecorded(courseInfo.getIsRecorded())
                    .mainImageUrl(courseInfo.getMainImageUrl())
                    .descriptions(descriptions)
                    .level(courseInfo.getLevel())
                    .build();
        } catch (Exception e) {
            throw new CourseMappingException();
        }
    }

    private static String getDate(Course course) {
        LocalDate localDate = course.getStartDate();
        return String.format("%d-%02d", localDate.getYear(), localDate.getMonthValue());
    }
}
