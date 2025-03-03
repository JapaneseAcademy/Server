package yeri_nihongo.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeri_nihongo.common.domain.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member", indexes = {
        @Index(name = "idx_member_role", columnList = "role"),
        @Index(name = "idx_member_name", columnList = "name")
})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String name;
    private String phone;
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 500)
    private String note;


    @Builder
    public Member(String loginId, String name, String phone, LocalDate birth, Role role) {
        this.loginId = loginId;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.role = role;
    }
}
