package com.songify.infrastructure.controller.dto.response;

import com.songify.domain.crud.dto.SongDto;

public record PatchSongResponseDto(SongDto updatedSong) {
}
