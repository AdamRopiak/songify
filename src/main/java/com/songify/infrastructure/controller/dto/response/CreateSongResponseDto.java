package com.songify.infrastructure.controller.dto.response;

import com.songify.domain.crud.song.dto.SongDto;

public record CreateSongResponseDto(SongDto song) {
}
