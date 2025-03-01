package yeri_nihongo.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri_nihongo.time.domain.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}
