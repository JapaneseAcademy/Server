package yeri_nihongo.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.time.domain.TimeBlock;

import java.util.List;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {

    @Query("SELECT tb " +
            "FROM TimeBlock tb " +
            "WHERE tb.timeTable.id = :timeTableId")
    List<TimeBlock> findTimeBlocksByTimeTableId(@Param("timeTableId") Long timeTableId);
}
