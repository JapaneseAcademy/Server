package yeri_nihongo.course.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "descriptionId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseInfoId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseInfo courseInfo;

    @Column(nullable = false, length = 2048)
    private String imageUrl;
}
