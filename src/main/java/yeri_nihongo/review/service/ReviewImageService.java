package yeri_nihongo.review.service;

import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.review.domain.Review;

import java.util.List;

public interface ReviewImageService {

    void createReviewImages(Review review, List<MultipartFile> images);
}
