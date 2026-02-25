package com.songify.song.error;

import org.springframework.http.HttpStatus;

public record ErrorDeleteSongResponsDto(String message, HttpStatus status) {

}
