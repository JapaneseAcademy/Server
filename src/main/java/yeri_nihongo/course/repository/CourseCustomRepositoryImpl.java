package yeri_nihongo.course.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.QCourse;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseCustomRepositoryImpl implements CourseCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Course> searchWithFilter(String filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QCourse course = QCourse.course;

        String[] date = filter.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);

        builder.and(course.startDate.year().eq(year).and(course.startDate.month().eq(month)));

        return queryFactory
                .selectFrom(course)
                .where(builder)
                .fetch();
    }

    @Override
    public Optional<Course> findCurrentCourseByCourseInfoId(Long courseInfoId) {
        QCourse course = QCourse.course;
        LocalDate today = LocalDate.now();
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
