package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PatchSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {

    public static SongDto mapFromSongToSongDto(SongEntity song) {
        return new SongDto(song.getId(), song.getSongName(), song.getArtistName());
    }
    public static SongEntity mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new SongEntity(dto.songName(), dto.artistName());
    }
    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongEntity newSong) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(newSong);
        return new CreateSongResponseDto(songDto);
    }
    public static GetAllSongsResponsDto mapFromSongToGetAllSongsResonseDto(List<SongEntity> allSongs) {
        List<SongDto> songDtos = allSongs.stream()
                .map(song -> SongMapper.mapFromSongToSongDto(song))
                .toList();
        return new GetAllSongsResponsDto(songDtos);
    }
    public static GetSongResponseDto mapFromSongtoGetSongResponseDto(SongEntity song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }

    public static DeleteSongResponsDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponsDto("You deleted song with id: " + id, HttpStatus.OK);
    }
    public static PutSongResponseDto mapFromSongToPutResponseDto(PutSongRequestDto dto) {
        return new PutSongResponseDto(dto.songName(), dto.artistName());
    }

    public static SongEntity mapFromUpdateSongRequestDtoTOSong(PutSongRequestDto song) {
        return new SongEntity(song.songName(), song.artistName());
    }
    public static PutSongResponseDto mapFromSongToPutSongResponseDto(SongEntity newSong) {
        return new PutSongResponseDto(newSong.getSongName(),  newSong.getArtistName());
    }
    public static SongEntity mapFromPatchSongRequestDtoToSong(PatchSongRequestDto dto) {
        return new SongEntity(dto.songName(), dto.artistName());
    }
    public static PatchSongResponseDto mapFromSongTopatchSongRequestResponseDto(SongEntity updatedSong) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(updatedSong);
        return new PatchSongResponseDto(songDto);
    }
}
