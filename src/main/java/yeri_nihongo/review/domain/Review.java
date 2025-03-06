package yeri_nihongo.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import yeri_nihongo.common.domain.BaseTimeEntity;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.domain.Enrollment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollmentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseInfoId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseInfo courseInfo;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 1000)
    private String review;

    private Boolean isAnonymous;
    private Boolean isForMain = false;
    private Boolean isBest = false;
    private Boolean isVisible = true;

    @Builder
    public Review(Enrollment enrollment, CourseInfo courseInfo, String title, String review, Boolean isAnonymous) {
        this.enrollment = enrollment;
        this.courseInfo = courseInfo;
        this.title = title;
        this.review = review;
        this.isAnonymous = isAnonymous;
    }

    public void toggleVisibility() {
        this.isVisible = !this.isVisible;
    }

    public void toggleForMain() {
        this.isForMain = !this.isForMain;
    }

    public void toggleBest() {
        this.isBest = !this.isBest;
    }
}
