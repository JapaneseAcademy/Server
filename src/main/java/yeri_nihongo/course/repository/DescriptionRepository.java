package yeri_nihongo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeri_nihongo.course.domain.Description;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {

    @Query("SELECT d.imageUrl " +
            "FROM Description d " +
            "WHERE d.courseInfo.id = :courseInfoId")
    List<String> getDescriptionImageUrlsByCourseInfoId(Long courseInfoId);
}
