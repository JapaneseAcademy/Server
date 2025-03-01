package yeri_nihongo.course.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CourseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseInfoId")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private Boolean isLive;

    @Column(nullable = false)
    private Boolean isOnline;

    @Column(nullable = false)
    private Boolean isRecorded;

    @Enumerated(EnumType.ORDINAL)
    private Level level;
}