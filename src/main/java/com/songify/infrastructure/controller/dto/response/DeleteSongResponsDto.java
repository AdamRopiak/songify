package com.songify.infrastructure.controller.dto.response;

import org.springframework.http.HttpStatus;

public record DeleteSongResponsDto(String message, HttpStatus status) {

}
