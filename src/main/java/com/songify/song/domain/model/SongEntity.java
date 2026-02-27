package com.songify.song.domain.model;

import lombok.Builder;

@Builder
public record SongEntity(String songName, String artistName) {
}
