package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final S3Service s3Service;
    private final RedisService redisService;

    private static final String CALENDAR_PATH = "calendar/";

    private static final String CALENDAR_KEY = "CalendarId";
    private static final String YOUTUBE_KEY = "YoutubeId";

    public void updateCalendar(MultipartFile calendar) {
        String calendarImageUrl = s3Service.uploadImage(calendar, CALENDAR_PATH);

        redisService.saveImageUrl(CALENDAR_KEY, calendarImageUrl + ",https://yeri-nihongo-bucket.s3.ap-northeast-2.amazonaws.com/6%EC%9B%94%EA%B0%9C%ED%8E%B8%EC%8D%B8%EB%84%A4%EC%9D%BC/1000026711.png");
    }

    public List<String> getCalendar() {
        String calendarImageUrl = redisService.getImageUrl(CALENDAR_KEY);
        return Arrays.asList(calendarImageUrl.split(","));
    }

    public void updateYoutube(String youtubeUrl) {
        redisService.saveImageUrl(YOUTUBE_KEY, youtubeUrl);
    }

    public String getYoutube() {
        return redisService.getImageUrl(YOUTUBE_KEY);
    }
}
