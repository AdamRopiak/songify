package com.songify.infrastructure.controller;

import com.songify.domain.crud.SongCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.controller.dto.response.*;
import com.songify.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.controller.dto.request.PutSongRequestDto;
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


    private final SongCrudFacade songCrudFacade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponsDto> getAllSongs(@PageableDefault(page = 0, size = 15) Pageable pageable) {
        List<SongDto> songRetrieverAllSongs = songCrudFacade.findAll(pageable);
        GetAllSongsResponsDto responsList = SongControllerMapper.mapFromSongToGetAllSongsResonseDto(songRetrieverAllSongs);
        return ResponseEntity.ok(responsList);
    }



    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto song = songCrudFacade.findSongDtoById(id);
        GetSongResponseDto respons = SongControllerMapper.mapFromSongtoGetSongResponseDto(song);
        return ResponseEntity.ok(respons);
    }


    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postNewSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongDto newSong = SongControllerMapper.mapFromCreateSongRequestDtoToSongDto(request);
        SongDto savedSong = songCrudFacade.addSong(newSong);
        CreateSongResponseDto body = SongControllerMapper.mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponsDto> deleteSongById(@PathVariable Long id) {
        songCrudFacade.deteById(id);
        DeleteSongResponsDto response = SongControllerMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PutSongResponseDto> updateSongResponseDtoResponseEntity(@PathVariable Long id,
                                                                                  @RequestBody
                                                                                     @Valid PutSongRequestDto request) {

        SongDto newSong = SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto(request);
        songCrudFacade.updateSongById(id, newSong);
        PutSongResponseDto body = SongControllerMapper.mapFromSongToPutSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<PatchSongResponseDto> patchSongResponseEntity(@PathVariable Long id,
                                                                        @RequestBody PatchSongRequestDto request) {
        SongDto updatedSong = SongControllerMapper.mapFromPatchSongRequestDtoToSong(request);
        SongDto savedSong = songCrudFacade.updatePartiallySongById(id, updatedSong);
        PatchSongResponseDto body =  SongControllerMapper.mapFromSongTopatchSongRequestResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }




}
