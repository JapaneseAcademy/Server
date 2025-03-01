package yeri_nihongo.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import yeri_nihongo.enrollment.domain.Enrollment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollmentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Enrollment enrollment;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 500)
    private String review;

    private Boolean isAnonymous;
    private Boolean isForMain = false;
    private Boolean isBest = false;
    private Boolean isVisible = true;


}
