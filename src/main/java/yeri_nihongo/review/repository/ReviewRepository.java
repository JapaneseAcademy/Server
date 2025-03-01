package yeri_nihongo.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.review.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT ri.imageUrl " +
            "FROM Review r " +
            "LEFT JOIN ReviewImage ri ON r.id = ri.review.id " +
            "WHERE r.id = :reviewId")
    List<String> getImageUrlsByReviewId(@Param("reviewId") Long reviewId);

    @Query("SELECT m.name " +
            "FROM Review r " +
            "JOIN r.enrollment e " +
            "JOIN e.member m " +
            "WHERE r.id = :reviewId")
    String getNameByReviewId(@Param("reviewId") Long reviewId);
}
