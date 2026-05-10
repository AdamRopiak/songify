package com.songify.domain.crud.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AlbumDto(Long albumId, String albumTitle) {
}
