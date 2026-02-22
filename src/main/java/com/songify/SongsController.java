package com.songify;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SongsController {

    Map<Integer, String> database = new HashMap<>();


    @GetMapping("/songs")
    public ResponseEntity<SongResponsDto> getAllSongs() {
        database.put(1, "Shawn Menes song");
        database.put(2, "Rihiana kap kap");
        SongResponsDto responsList = new SongResponsDto(database);
        return ResponseEntity.ok(responsList);
    }
    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable Integer id) {
        String song = database.get(id);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SingleSongResponseDto responsList = new SingleSongResponseDto(song);
        return ResponseEntity.ok(responsList);
    }
}
