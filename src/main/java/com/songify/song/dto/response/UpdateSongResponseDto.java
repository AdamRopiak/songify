package com.songify.song.dto.response;

import com.songify.song.controller.SongEntity;

public record UpdateSongResponseDto(String songName, String artistName) {
}
