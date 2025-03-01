package yeri_nihongo.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.dto.response.ReviewProjection;

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

    @Query("SELECT r.id AS id, r.title AS title, r.review AS review, r.createdAt AS createdAt " +
            "FROM Review r " +
            "WHERE r.isVisible = true and r.courseInfo.id = :courseInfoId")
    Page<ReviewProjection> getReviewByCourseInfoId(@Param("courseInfoId") Long courseInfoId, Pageable pageable);
}
