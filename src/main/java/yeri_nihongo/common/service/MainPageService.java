package yeri_nihongo.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.common.dto.response.CalendarResponse;
import yeri_nihongo.course.repository.InstructorRepository;
import yeri_nihongo.exception.member.UserNotFoundException;
import yeri_nihongo.member.domain.Instructor;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final S3Service s3Service;
    private final RedisService redisService;
    private final InstructorRepository instructorRepository;

    private static final String CALENDAR_PATH = "calendar/";

    private static final String CALENDAR_KEY = "CalendarId";
    private static final String YOUTUBE_KEY = "YoutubeId";

    public void updateCalendar(Long instructorId, MultipartFile calendar) {
        String calendarImageUrl = s3Service.uploadImage(calendar, CALENDAR_PATH);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new UserNotFoundException(instructorId));

        instructor.updateCalendarUrl(calendarImageUrl);
        instructorRepository.save(instructor);
    }

    public List<String> getCalendar() {
        String calendarImageUrl = redisService.getImageUrl(CALENDAR_KEY);
        return Arrays.asList(calendarImageUrl.split(","));
    }

    public List<CalendarResponse> getCalendars() {
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream()
                .map(instructor -> CalendarResponse.builder()
                        .instructorId(instructor.getId())
                        .calendarUrl(instructor.getCalendarUrl())
                        .build())
                .toList();
    }

    public void updateYoutube(String youtubeUrl) {
        redisService.saveImageUrl(YOUTUBE_KEY, youtubeUrl);
    }

    public String getYoutube() {
        return redisService.getImageUrl(YOUTUBE_KEY);
    }
}
