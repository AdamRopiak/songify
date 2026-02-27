package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongMapper;
import com.songify.song.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.model.SongEntity;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.songify.song.domain.service.SongMapper.mapFromPatchSongRequestDtoToSong;
import static com.songify.song.domain.service.SongMapper.mapFromSongToPutResponseDto;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    Map<Integer, SongEntity> database = new HashMap<>(Map.of(
            1, new SongEntity("Shawn Menes song", "Shawn Mendes"),
            2, new SongEntity("Rihiana kap kap", "Rhianna"),
            3, new SongEntity("Shawn Menes song2", "Metallica"),
            4, new SongEntity("Rihiana kap kap2", "Kapuś")
    ));


    @GetMapping
    public ResponseEntity<GetAllSongsResponsDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, SongEntity> limitedMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            GetAllSongsResponsDto response = new GetAllSongsResponsDto(limitedMap);
            return ResponseEntity.ok(response);
        }

        GetAllSongsResponsDto responsList = SongMapper.mapFromSongToGetAllSongsResonseDto(database);
        return ResponseEntity.ok(responsList);
    }



    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity song = database.get(id);
        GetSongResponseDto respons = SongMapper.mapFromSongtoGetSongResponseDto(song);
        return ResponseEntity.ok(respons);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postNewSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity newSong = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        log.info("Adding new song: " + newSong);
        database.put(database.size() + 1, newSong);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Integer id) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity song = database.remove(id);
        DeleteSongResponsDto response = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PutSongResponseDto> updateSongResponseDtoResponseEntity(@PathVariable Integer id,
                                                                                  @RequestBody
                                                                                     @Valid PutSongRequestDto request) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity newSong = SongMapper.mapFromUpdateSongRequestDtoTOSong(request);
        SongEntity oldSongName = database.put(id, newSong);
        log.info("Updated song with id: " + id + "from: " + oldSongName.songName() + " to: " + newSong.songName() +
                " and artist: old artist " + oldSongName.artistName() + "to: " + newSong.artistName());
        PutSongResponseDto body = SongMapper.mapFromSongToPutSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongResponseEntity(@PathVariable Integer id,
                                                                        @RequestBody PatchSongRequestDto request) {
        if (!database.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity songFromDatabase = database.get(id);
        SongEntity updatedSong = SongMapper.mapFromPatchSongRequestDtoToSong(request);
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if(request.songName()!=null){
            builder.songName((request.songName()));
            log.info("Partially update song");
        }else{
            builder.songName(songFromDatabase.songName());
            log.info("Partially update artist");
        }
        if(request.artistName()!=null){
            builder.artistName((request.artistName()));
        }else{
            builder.artistName(songFromDatabase.artistName());
        }
        database.put(id, updatedSong);
        PatchSongResponseDto body =  SongMapper.mapFromSongTopatchSongRequestResponseDto(updatedSong);
        return ResponseEntity.ok(body);
    }




}
