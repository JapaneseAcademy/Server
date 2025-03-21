package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final S3Service s3Service;
    private final RedisService redisService;

    private static final String CALENDER_PATH = "calender/";

    private static final String CALENDER_KEY = "CalenderId";
    private static final String YOUTUBE_KEY = "YoutubeId";

    public void updateCalender(MultipartFile calender) {
        String calenderImageUrl = s3Service.uploadImage(calender, CALENDER_PATH);

        redisService.saveImageUrl(CALENDER_KEY, calenderImageUrl);
    }

    public String getCalender() {
        return redisService.getImageUrl(CALENDER_KEY);
    }

    public void updateYoutube(String youtubeUrl) {
        redisService.saveImageUrl(YOUTUBE_KEY, youtubeUrl);
    }

    public String getYoutube() {
        return redisService.getImageUrl(YOUTUBE_KEY);
    }
}
