package yeri_nihongo.common.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.common.dto.response.CalendarResponse;
import yeri_nihongo.common.service.MainPageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/youtube")
    public ResponseEntity<YoutubeDto> getYoutubeUrl() {
        String youtubeId = mainPageService.getYoutube();

        return ResponseEntity.ok(new YoutubeDto(youtubeId));
    }

    @PutMapping("/youtube")
    public ResponseEntity<HttpStatus> saveYoutubeUrl(
            @RequestBody @Valid YoutubeDto youtubeDto
    ) {
        mainPageService.updateYoutube(youtubeDto.getYoutubeId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponseDto> getCalendar() {
        List<String> calendar = mainPageService.getCalendar();

        return ResponseEntity.ok(new CalendarResponseDto(calendar));
    }

//    @PutMapping("/calendar")
//    public ResponseEntity<HttpStatus> saveCalendar(
//            @RequestPart MultipartFile calendar
//    ) {
//        mainPageService.updateCalendar(calendar);
//
//        return ResponseEntity.ok().build();
//    }

    @Getter
    @AllArgsConstructor
    public static class YoutubeDto {
        private String youtubeId;
    }

    @Getter
    @AllArgsConstructor
    public static class CalendarResponseDto {
        private List<String> calendar;
    }

    @GetMapping("/calendars")
    public ResponseEntity<List<CalendarResponse>> getCalendars() {
        List<CalendarResponse> calendars = mainPageService.getCalendars();

        return ResponseEntity.ok(calendars);
    }

    @PutMapping("/instructor/{instructorId}/calendar")
    public ResponseEntity<Void> updateCalendarByInstructor(
            @PathVariable Long instructorId,
            @RequestPart MultipartFile calendar
    ) {
        mainPageService.updateCalendar(instructorId, calendar);
        return ResponseEntity.ok().build();
    }
}
