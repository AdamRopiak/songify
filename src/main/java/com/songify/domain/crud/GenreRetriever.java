package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class GenreRetriever {
    private final GenreRepository genreRepository;

    GenreEntity findGenreById(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + genreId + " not found"));
    }

    Set<GenreDto> findAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(genreEntity -> new GenreDto(
                        genreEntity.getGenreId(),
                        genreEntity.getGenreName()
                ))
                .collect(Collectors.toSet());
    }
}
