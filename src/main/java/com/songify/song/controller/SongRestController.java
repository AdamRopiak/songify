package com.songify.song.controller;

import com.songify.song.dto.request.SongRequestDto;
import com.songify.song.dto.request.UpdateSongRequestDto;
import com.songify.song.dto.response.DeleteSongResponsDto;
import com.songify.song.dto.response.SingleSongResponseDto;
import com.songify.song.dto.response.SongResponsDto;
import com.songify.song.dto.response.UpdateSongResponseDto;
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

    Map<Integer, SongEntity> database = new HashMap<>(Map.of(
            1, new SongEntity("Shawn Menes song", "Shawn Mendes"),
            2, new SongEntity("Rihiana kap kap", "Rhianna"),
            3, new SongEntity("Shawn Menes song2", "Metallica"),
            4, new SongEntity("Rihiana kap kap2", "Kapuś")
    ));


    @GetMapping("/songs")
    public ResponseEntity<SongResponsDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, SongEntity> limitedMap = database.entrySet()
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
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity song = database.get(id);
        SingleSongResponseDto respons = new SingleSongResponseDto(song);
        return ResponseEntity.ok(respons);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postNewSong(@RequestBody @Valid SongRequestDto request) {
        SongEntity newSong = new SongEntity(request.songName(), request.artistName());
        log.info("Adding new song: " + newSong);
        database.put(database.size() + 1, newSong);
        return ResponseEntity.ok(new SingleSongResponseDto(newSong));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        database.remove(id);
        DeleteSongResponsDto response = new DeleteSongResponsDto("Song deleted id: " + id, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<UpdateSongResponseDto> updateSongResponseDtoResponseEntity(@PathVariable Integer id,
                                                                                     @RequestBody
                                                                                     @Valid UpdateSongRequestDto update) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        String newSongName = update.songName();
        String newArtist = update.artistName();
        SongEntity newSong = new SongEntity(newSongName, newArtist);
        SongEntity oldSongName = database.put(id, newSong);
        log.info("Updated song with id: " + id + "from: " + oldSongName.songName() + " to: " + newSong.songName() +
                " and artist: old artist " + oldSongName.artistName() + "to: " + newSong.artistName());
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.songName(), newSongName));
    }
}
