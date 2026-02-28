package com.songify.song.infrastructure.controller;

import com.songify.song.domain.repository.SongRepository;
import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongRetriever;
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

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    public SongRestController(SongAdder songAdder, SongRetriever songRetriever) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
    }


    @GetMapping
    public ResponseEntity<GetAllSongsResponsDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        Map<Integer, SongEntity> songRetrieverAllSongs = songRetriever.findAll();
        if(limit!=null) {
            Map<Integer, SongEntity> limitedMap = songRetriever.findAllLimitedBy(limit);
            GetAllSongsResponsDto response = new GetAllSongsResponsDto(limitedMap);
                return ResponseEntity.ok(response);
            }
        GetAllSongsResponsDto responsList = SongMapper.mapFromSongToGetAllSongsResonseDto(songRetrieverAllSongs);
        return ResponseEntity.ok(responsList);
    }



    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Map<Integer, SongEntity> songRetrieverAll = songRetriever.findAll();
        if (!songRetrieverAll.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity song = songRetrieverAll.get(id);
        GetSongResponseDto respons = SongMapper.mapFromSongtoGetSongResponseDto(song);
        return ResponseEntity.ok(respons);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postNewSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity newSong = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        songAdder.addSong(newSong);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Integer id) {

        if (!getAll().containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        Map<Integer, SongEntity> songRetrieverAll = songRetriever.findAll();
        SongEntity song = songRetrieverAll.remove(id);
        DeleteSongResponsDto response = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(response);
    }

    private Map<Integer, SongEntity> getAll() {
        Map<Integer, SongEntity> songRetrieverAll = songRetriever.findAll();
        return songRetrieverAll;
    }


    @PutMapping("/{id}")
    public ResponseEntity<PutSongResponseDto> updateSongResponseDtoResponseEntity(@PathVariable Integer id,
                                                                                  @RequestBody
                                                                                     @Valid PutSongRequestDto request) {
        Map<Integer, SongEntity> songRetrieverAll = songRetriever.findAll();
        if (!songRetrieverAll.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity newSong = SongMapper.mapFromUpdateSongRequestDtoTOSong(request);
        SongEntity oldSongName = songRetrieverAll.put(id, newSong);
        log.info("Updated song with id: " + id + "from: " + oldSongName.songName() + " to: " + newSong.songName() +
                " and artist: old artist " + oldSongName.artistName() + "to: " + newSong.artistName());
        PutSongResponseDto body = SongMapper.mapFromSongToPutSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongResponseEntity(@PathVariable Integer id,
                                                                        @RequestBody PatchSongRequestDto request) {
        Map<Integer, SongEntity> songRetrieverAll = songRetriever.findAll();
        if (!songRetrieverAll.containsKey(id)) {
            throw new SongNotFoundException("Song with " + id + " not found");
        }
        SongEntity songFromDatabase = songRetrieverAll.get(id);
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
        songRetrieverAll.put(id, updatedSong);
        PatchSongResponseDto body =  SongMapper.mapFromSongTopatchSongRequestResponseDto(updatedSong);
        return ResponseEntity.ok(body);
    }




}
