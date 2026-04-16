package com.songify.domain.crud.dto;

import java.time.Instant;

public record AlbumRequestDto(String albumTitle, Instant releaseDate, Long songId) {
}
