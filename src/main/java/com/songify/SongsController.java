package com.songify;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongsController {

    Map<Integer, String> database = new HashMap<>();


   @GetMapping("/songs")
    public ResponseEntity<SongResponsDto> getAllSongs(@RequestParam(required = false) Integer limit) {
       database.put(1, "Shawn Menes song");
       database.put(2, "Rihiana kap kap");
       if(limit != null) {
           Map<Integer, String> limitedMap = database.entrySet()
                   .stream()
                   .limit(limit)
                   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
           SongResponsDto response = new SongResponsDto(limitedMap);
            return ResponseEntity.ok(response);
       }

       SongResponsDto responsList = new SongResponsDto(database);
       return ResponseEntity.ok(responsList);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
       log.info(requestId);
       String song = database.get(id);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SingleSongResponseDto responsList = new SingleSongResponseDto(song);
        return ResponseEntity.ok(responsList);
    }

}
