package com.songify.domain.crud.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record AlbumDto(Long albumId, String albumTitle, Set<Long> songsIds) {
}
