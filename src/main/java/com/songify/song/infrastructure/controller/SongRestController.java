package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongDeleter;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.service.SongUpdater;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;


    @GetMapping
    public ResponseEntity<GetAllSongsResponsDto> getAllSongs(@PageableDefault(page = 0, size = 15) Pageable pageable) {
        List<SongEntity> songRetrieverAllSongs = songRetriever.findAll(pageable);
        GetAllSongsResponsDto responsList = SongMapper.mapFromSongToGetAllSongsResonseDto(songRetrieverAllSongs);
        return ResponseEntity.ok(responsList);
    }



    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongEntity song = songRetriever.findSongById(id);
        GetSongResponseDto respons = SongMapper.mapFromSongtoGetSongResponseDto(song);
        return ResponseEntity.ok(respons);
    }


    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postNewSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity newSong = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        SongEntity savedSong = songAdder.addSong(newSong);
        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Long id) {
        songDeleter.deteById(id);
        DeleteSongResponsDto response = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(response);
    }

   @GetMapping("/test")
   public ResponseEntity<GetSongResponseDto> test(){
        songRetriever.compareSongs();
        return ResponseEntity.ok().build();
   }


    @PutMapping("/{id}")
    public ResponseEntity<PutSongResponseDto> updateSongResponseDtoResponseEntity(@PathVariable Long id,
                                                                                  @RequestBody
                                                                                     @Valid PutSongRequestDto request) {

        SongEntity newSong = SongMapper.mapFromUpdateSongRequestDtoTOSong(request);
        songUpdater.updateSongById(id, newSong);
        PutSongResponseDto body = SongMapper.mapFromSongToPutSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongResponseEntity(@PathVariable Long id,
                                                                        @RequestBody PatchSongRequestDto request) {
        SongEntity updatedSong = SongMapper.mapFromPatchSongRequestDtoToSong(request);
        SongEntity savedSong = songUpdater.updatePartiallySongById(id, updatedSong);
        PatchSongResponseDto body =  SongMapper.mapFromSongTopatchSongRequestResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }




}
