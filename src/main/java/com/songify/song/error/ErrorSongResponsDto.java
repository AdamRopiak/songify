package com.songify.song.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponsDto(String message, HttpStatus status) {

}
