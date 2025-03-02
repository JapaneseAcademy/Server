package yeri_nihongo.time.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import yeri_nihongo.course.domain.Course;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeTableId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    private int studentCount = 0;

    @Builder
    public TimeTable(Course course) {
        this.course = course;
    }
}
