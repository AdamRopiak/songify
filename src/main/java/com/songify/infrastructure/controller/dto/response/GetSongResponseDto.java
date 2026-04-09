package com.songify.infrastructure.controller.dto.response;

import com.songify.domain.crud.dto.SongDto;

public record GetSongResponseDto(SongDto song) {
}
