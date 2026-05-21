package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.*;
import com.songify.infrastructure.crud.song.controller.dto.request.PutSongRequestDto;
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


    private final SongifyCrudFacade songifyCrudFacade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponsDto> getAllSongs(@PageableDefault(page = 0, size = 15) Pageable pageable) {
        List<SongDto> songRetrieverAllSongs = songifyCrudFacade.findAllSongs(pageable);
        GetAllSongsResponsDto responsList = SongControllerMapper.mapFromSongToGetAllSongsResonseDto(songRetrieverAllSongs);
        return ResponseEntity.ok(responsList);
    }



    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto song = songifyCrudFacade.findSongDtoById(id);
        GetSongResponseDto respons = SongControllerMapper.mapFromSongtoGetSongResponseDto(song);
        return ResponseEntity.ok(respons);
    }


    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postNewSong(@RequestBody @Valid SongRequestDto request) {
        SongDto savedSong = songifyCrudFacade.addSong(request);
        CreateSongResponseDto body = SongControllerMapper.mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Long id) {
        songifyCrudFacade.deteteSongById(id);
        DeleteSongResponsDto response = SongControllerMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PutSongResponseDto> updateSongResponseDtoResponseEntity(@PathVariable Long id,
                                                                                  @RequestBody
                                                                                     @Valid PutSongRequestDto request) {

        SongDto newSong = SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto(request);
        songifyCrudFacade.updateSongById(id, newSong);
        PutSongResponseDto body = SongControllerMapper.mapFromSongToPutSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/{songId}/genres/{genreId}")
    public ResponseEntity<String> assingGenreToSong(@PathVariable Long songId,
                                                    @PathVariable Long genreId){
        songifyCrudFacade.assingGenreToSong(genreId, songId);
        return ResponseEntity.ok("Updated");
    }



    @PatchMapping("/{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongResponseEntity(@PathVariable Long id,
                                                                        @RequestBody PatchSongRequestDto request) {
        SongDto updatedSong = SongControllerMapper.mapFromPatchSongRequestDtoToSong(request);
        SongDto savedSong = songifyCrudFacade.updatePartiallySongById(id, updatedSong);
        PatchSongResponseDto body =  SongControllerMapper.mapFromSongTopatchSongRequestResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }




}
