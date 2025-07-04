package yeri_nihongo.course.domain;

import jakarta.persistence.*;
import lombok.*;
import yeri_nihongo.member.domain.Instructor;

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

    @Column(nullable = false, length = 2048)
    private String mainImageUrl;

    @Column(nullable = false)
    private Boolean isLive;

    @Column(nullable = false)
    private Boolean isOnline;

    @Column(nullable = false)
    private Boolean isRecorded;

    @Column(nullable = false)
    private Boolean isLiveOnline;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "instructorId", nullable = true)
    private Instructor instructor;
}