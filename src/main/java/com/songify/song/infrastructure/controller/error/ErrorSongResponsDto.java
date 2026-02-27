package com.songify.song.infrastructure.controller.error;

import org.springframework.http.HttpStatus;

public record ErrorSongResponsDto(String message, HttpStatus status) {

}
