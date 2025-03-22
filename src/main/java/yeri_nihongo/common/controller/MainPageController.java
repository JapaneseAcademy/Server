package yeri_nihongo.common.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yeri_nihongo.common.service.MainPageService;

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
        String calender = mainPageService.getCalendar();

        return ResponseEntity.ok(new CalendarResponseDto(calender));
    }

    @PutMapping("/calendar")
    public ResponseEntity<HttpStatus> saveCalendar(
            @RequestPart MultipartFile calendar
    ) {
        mainPageService.updateCalendar(calendar);

        return ResponseEntity.ok().build();
    }

    @Getter
    @AllArgsConstructor
    public static class YoutubeDto {
        private String youtubeId;
    }

    @Getter
    @AllArgsConstructor
    public static class CalendarResponseDto {
        private String calendar;
    }
}
