package yeri_nihongo.enrollment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import yeri_nihongo.common.domain.BaseTimeEntity;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.time.domain.TimeTable;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Enrollment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollmentId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeTableId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TimeTable timeTable;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer paymentAmount;

    @Column(nullable = false)
    private LocalDateTime paymentAt;

    @Column(nullable = false, unique = true)
    private String paymentKey;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private String method;
}
