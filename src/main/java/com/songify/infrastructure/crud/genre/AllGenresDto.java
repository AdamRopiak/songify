package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.dto.GenreDto;

import java.util.List;
import java.util.Set;

public record AllGenresDto (Set<GenreDto> genres){

}
