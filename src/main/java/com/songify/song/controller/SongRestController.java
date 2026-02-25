package com.songify.song.controller;

import com.songify.song.dto.DeleteSongResponsDto;
import com.songify.song.dto.SingleSongResponseDto;
import com.songify.song.dto.SongRequestDto;
import com.songify.song.dto.SongResponsDto;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongRestController {

    Map<Integer, String> database = new HashMap<>(Map.of(
            1, "Shawn Menes song",
            2, "Rihiana kap kap",
            3, "Shawn Menes song2",
            4, "Rihiana kap kap2"
    ));


   @GetMapping("/songs")
    public ResponseEntity<SongResponsDto> getAllSongs(@RequestParam(required = false) Integer limit) {
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

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postNewSong(@RequestBody @Valid SongRequestDto request){
       String newSong = request.songName();
       log.info("Adding new song: " + newSong);
       database.put(database.size()+1, newSong );
       return ResponseEntity.ok(new SingleSongResponseDto(newSong));
    }
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Integer id){
       if(!database.containsKey(id)) {
           throw new SongNotFoundException("Song with " + id + " not found");
       }
       database.remove(id);
       DeleteSongResponsDto response = new DeleteSongResponsDto("Song deleted id: "+id, HttpStatus.OK);
       return ResponseEntity.ok(response);
   }

}
