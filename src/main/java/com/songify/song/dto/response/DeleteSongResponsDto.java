package com.songify.song.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteSongResponsDto(String message, HttpStatus status) {

}
