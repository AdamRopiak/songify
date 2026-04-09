package com.songify.infrastructure.controller.dto.response;

import com.songify.domain.crud.dto.SongDto;

import java.util.List;

public record GetAllSongsResponsDto(List<SongDto> songs) {
}
