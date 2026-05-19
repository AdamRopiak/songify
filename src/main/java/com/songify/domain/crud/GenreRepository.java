package com.songify.domain.crud;


import com.songify.domain.crud.dto.GenreDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

interface GenreRepository extends Repository<GenreEntity, Long> {

    GenreEntity save(GenreEntity newGenre);

    Optional<GenreEntity> findById(Long genreId);


    int deleteByGenreId(Long genreId);

    Set<GenreEntity> findAll();
}
