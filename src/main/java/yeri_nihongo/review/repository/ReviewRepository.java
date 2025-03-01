package yeri_nihongo.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeri_nihongo.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
