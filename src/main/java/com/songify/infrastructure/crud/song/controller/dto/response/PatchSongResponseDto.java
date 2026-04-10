package com.songify.infrastructure.crud.song.controller.dto.response;

import com.songify.domain.crud.dto.SongDto;

public record PatchSongResponseDto(SongDto updatedSong) {
}
