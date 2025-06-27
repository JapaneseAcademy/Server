package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri_nihongo.member.domain.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
