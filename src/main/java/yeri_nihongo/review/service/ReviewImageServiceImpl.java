package yeri_nihongo.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.common.service.S3Service;
import yeri_nihongo.review.domain.Review;
import yeri_nihongo.review.domain.ReviewImage;
import yeri_nihongo.review.repository.ReviewImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageServiceImpl implements ReviewImageService {

    private final S3Service s3Service;

    private final ReviewImageRepository reviewImageRepository;

    private static final String path = "reviews/";

    @Override
    public void createReviewImages(Review review, List<MultipartFile> images) {
        images.forEach(image -> {
            String imageUrl = s3Service.uploadImage(image, path + review.getId() + "/");
            ReviewImage reviewImage = ReviewImage.builder()
                    .review(review)
                    .imageUrl(imageUrl)
                    .build();

            reviewImageRepository.save(reviewImage);
        });
    }
}
