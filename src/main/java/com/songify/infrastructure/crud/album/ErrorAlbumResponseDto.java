package com.songify.infrastructure.crud.album;

import org.springframework.http.HttpStatus;

record ErrorAlbumResponseDto (String message, HttpStatus status) {
}
