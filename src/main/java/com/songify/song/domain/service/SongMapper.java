package com.songify.song.domain.service;

import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import javax.sound.midi.Patch;
import java.util.Map;

public class SongMapper {
    public static SongEntity mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new SongEntity(dto.songName(), dto.artistName());
    }
    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongEntity newSong) {
        return new CreateSongResponseDto(newSong);
    }
    public static GetAllSongsResponsDto mapFromSongToGetAllSongsResonseDto(Map<Integer, SongEntity> database) {
        return new GetAllSongsResponsDto(database);
    }
    public static GetSongResponseDto mapFromSongtoGetSongResponseDto(SongEntity song) {
        return new GetSongResponseDto(song);
    }

    public static DeleteSongResponsDto mapFromSongToDeleteSongResponseDto(Integer id) {
        return new DeleteSongResponsDto("You deleted song with id: " + id, HttpStatus.OK);
    }
    public static PutSongResponseDto mapFromSongToPutResponseDto(PutSongRequestDto dto) {
        return new PutSongResponseDto(dto.songName(), dto.artistName());
    }

    public static SongEntity mapFromUpdateSongRequestDtoTOSong(PutSongRequestDto song) {
        return new SongEntity(song.songName(), song.artistName());
    }
    public static PutSongResponseDto mapFromSongToPutSongResponseDto(SongEntity newSong) {
        return new PutSongResponseDto(newSong.songName(),  newSong.artistName());
    }
    public static SongEntity mapFromPatchSongRequestDtoToSong(PatchSongRequestDto dto) {
        return new SongEntity(dto.songName(), dto.artistName());
    }
    public static PatchSongResponseDto mapFromSongTopatchSongRequestResponseDto(SongEntity updatedSong) {
        return new PatchSongResponseDto(updatedSong);
    }
}
