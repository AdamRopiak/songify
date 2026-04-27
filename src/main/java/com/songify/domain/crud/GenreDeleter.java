package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreDeleter {

    private final GenreRepository genreRepository;

    void deleteById(Long genreId) {
        int i = genreRepository.deleteByGenreId(genreId);
        if (i != 1) {
            throw new GenreWasNotDeletedException("Can't delete genre with id: " + genreId);
        }
    }
}
