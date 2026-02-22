package com.songify;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SongsController {

    @GetMapping("/songs")
    public ResponseEntity<SongResponsDto> getAllSongs() {
        SongResponsDto responsList = new SongResponsDto(List.of("Shwanmendes song 1", "Rihiana kap kap"));
        return ResponseEntity.ok(responsList);
    }
}
