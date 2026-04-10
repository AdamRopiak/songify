package com.songify.infrastructure.crud.song.controller.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteSongResponsDto(String message, HttpStatus status) {

}
