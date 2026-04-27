package com.songify.domain.crud;


import org.springframework.data.repository.Repository;

interface GenreRepository extends Repository<GenreEntity, Long> {

    GenreEntity save(GenreEntity newGenre);


    int deleteByGenreId(Long genreId);
}
