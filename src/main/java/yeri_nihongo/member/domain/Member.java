package yeri_nihongo.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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

    @Column(length = 2048)
    private String imageUrl = "";

    @Column(length = 100)
    private String address = "";

    @Column(length = 500)
    private String notes = "";


    @Builder
    public Member(String loginId, String name, String phone, LocalDate birth, Role role, String imageUrl, String address, String notes) {
        this.loginId = loginId;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.role = role;
        this.imageUrl = imageUrl;
        this.address = address;
        this.notes = notes;
    }
}
