package com.songify.song.dto;

import org.springframework.http.HttpStatus;

public record DeleteSongResponsDto(String message, HttpStatus status) {

}
