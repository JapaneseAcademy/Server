package yeri_nihongo.common.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeri_nihongo.common.service.RedisService;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainPageController {

    private final RedisService redisService;

    @GetMapping("/youtube")
    public ResponseEntity<YoutubeDto> getYoutubeUrl() {
        String youtubeId = redisService.getYoutubeUrl();

        return ResponseEntity.ok(new YoutubeDto(youtubeId));
    }

    @PutMapping("/youtube")
    public ResponseEntity<HttpStatus> saveYoutubeUrl(
            @RequestBody @Valid YoutubeDto youtubeDto
    ) {
        redisService.saveYoutubeUrl(youtubeDto.getYoutubeId());

        return ResponseEntity.ok().build();
    }

    @Getter
    @AllArgsConstructor
    public static class YoutubeDto {
        private String youtubeId;
    }
}
