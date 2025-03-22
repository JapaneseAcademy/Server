package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        redisService.saveImageUrl(CALENDAR_KEY, calendarImageUrl);
    }

    public String getCalendar() {
        return redisService.getImageUrl(CALENDAR_KEY);
    }

    public void updateYoutube(String youtubeUrl) {
        redisService.saveImageUrl(YOUTUBE_KEY, youtubeUrl);
    }

    public String getYoutube() {
        return redisService.getImageUrl(YOUTUBE_KEY);
    }
}
