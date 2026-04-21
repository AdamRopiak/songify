package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ArtistRequestDto(
        @NotNull(message = "Artist name can't be null")
        @NotEmpty(message = "Artist name can't be empty")
        String artistName) {
}
