package yeri_nihongo.course.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseInfoId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseInfo courseInfo;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private int studentCount = 0;

    @Builder
    public Course(CourseInfo courseInfo, LocalDate startDate, LocalDate endDate) {
        this.courseInfo = courseInfo;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
