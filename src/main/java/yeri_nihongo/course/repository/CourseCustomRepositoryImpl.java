package yeri_nihongo.course.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Course;
import yeri_nihongo.course.domain.QCourse;
import yeri_nihongo.course.dto.request.CourseFilter;

import java.time.LocalDate;
import java.util.List;

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
}
