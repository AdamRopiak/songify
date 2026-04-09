package com.songify.infrastructure.controller;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.infrastructure.controller.dto.response.*;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongControllerMapper {


    static SongDto mapFromCreateSongRequestDtoToSongDto(CreateSongRequestDto dto) {
        return SongDto.builder()
                .songName(dto.songName())
                .build();
    }

    static SongDto mapFromUpdateSongRequestDtoToSongDto(PutSongRequestDto dto) {
        return SongDto.builder()
                .songName(dto.songName())
                .build();
    }

    static SongDto mapFromPatchSongRequestDtoToSong(PatchSongRequestDto dto) {
        return SongDto.builder()
                .songName(dto.songName())
                .build();
    }

    static GetSongResponseDto mapFromSongtoGetSongResponseDto(SongDto song) {
        return new GetSongResponseDto(song);
    }

    static DeleteSongResponsDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponsDto("You deleted song with id: " + id, HttpStatus.OK);
    }

    static PutSongResponseDto mapFromSongToPutSongResponseDto(SongDto newSong) {
        return new PutSongResponseDto(newSong.songName(), "Test");
    }

    static PatchSongResponseDto mapFromSongTopatchSongRequestResponseDto(SongDto savedSong) {
        return new PatchSongResponseDto(savedSong);
    }

    static GetAllSongsResponsDto mapFromSongToGetAllSongsResonseDto(List<SongDto> songRetrieverAllSongs) {
        return new GetAllSongsResponsDto(songRetrieverAllSongs);
    }

    static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongDto savedSong) {
        return new CreateSongResponseDto(savedSong);
    }
}
