package yeri_nihongo.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri_nihongo.time.domain.TimeBlock;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {
}
