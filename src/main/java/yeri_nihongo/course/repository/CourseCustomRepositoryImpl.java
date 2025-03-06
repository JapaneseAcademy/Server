package yeri_nihongo.course.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.QCourse;
import yeri_nihongo.course.dto.request.CourseFilter;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseCustomRepositoryImpl implements CourseCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Course> searchWithFilter(CourseFilter filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QCourse course = QCourse.course;

        if (filter.getDate() != null) {
            LocalDate date = filter.getDate();
            int year = date.getYear();
            int month = date.getMonthValue();

            builder.and(course.startDate.year().eq(year).and(course.startDate.month().eq(month)));
        }

        return queryFactory
                .selectFrom(course)
                .where(builder)
                .fetch();
    }

    @Override
//    public Course findCurrentCourseByCourseInfoId(Long courseInfoId) {
//        QCourse course = QCourse.course;
//        LocalDate date = LocalDate.now();
//
//        return queryFactory
//                .selectFrom(course)
//                .where(course.courseInfo.id.eq(courseInfoId)
//                        .and(course.startDate.gt(date)))
//                .orderBy(course.startDate.asc())
//                .limit(1)
//                .fetchOne();
//    }
    public Optional<Course> findCurrentCourseByCourseInfoId(Long courseInfoId) {
        QCourse course = QCourse.course;
//        LocalDate today = LocalDate.now();
        LocalDate today = LocalDate.now().plusDays(15);
        int dayOfMonth = today.getDayOfMonth();

        LocalDate targetMonth = (dayOfMonth <= 19) ? today : today.plusMonths(1);

        LocalDate firstDayOfTargetMonth = targetMonth.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfTargetMonth = targetMonth.with(TemporalAdjusters.lastDayOfMonth());

        return Optional.ofNullable(queryFactory
                .selectFrom(course)
                .where(course.courseInfo.id.eq(courseInfoId)
                        .and(course.startDate.between(firstDayOfTargetMonth, lastDayOfTargetMonth)))
                .orderBy(course.startDate.asc())
                .limit(1)
                .fetchOne());
    }
}
