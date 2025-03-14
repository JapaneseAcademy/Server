package yeri_nihongo.member.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m " +
            "FROM Member m " +
            "WHERE m.loginId = :loginId")
    Optional<Member> findByLoginId(@Param("loginId") String loginId);

    @Cacheable("students")
    @Query("SELECT m " +
            "FROM Member m " +
            "WHERE m.role = STUDENT " +
            "ORDER BY m.name ASC")
    List<Member> findAllStudent();

    @Query("SELECT m.phone " +
            "FROM Member m " +
            "WHERE m.id = :memberId")
    Optional<String> findPhoneByMemberId(@Param("memberId") Long memberId);
}
