package com.songify.domain.crud;


import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreAdder{
    GenreRepository genreRepository;

    GenreDto addGenre(String genreName){
        GenreEntity newGenre = new GenreEntity(genreName);
        GenreEntity savedGenre = genreRepository.save(newGenre);
        return new GenreDto(savedGenre.getGenreId(), savedGenre.getGenreName());
    }
}
