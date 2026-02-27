package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domain.model.SongEntity;

import java.util.Map;

public record GetAllSongsResponsDto(Map<Integer, SongEntity> songs) {
}
