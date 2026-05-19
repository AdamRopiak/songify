package com.songify.domain.crud;

import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryGenreRepository implements GenreRepository {
    Map<Long, GenreEntity> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(1);

    public InMemoryGenreRepository(){
        save(new GenreEntity(1L, "default"));
    }
    @Override
    public GenreEntity save(final GenreEntity newGenre) {
        long index = this.index.getAndIncrement();
        db.put(index, newGenre);
        newGenre.setGenreId(index);
        return newGenre;
    }

    @Override
    public Optional<GenreEntity> findById(Long genreId) {
        GenreEntity value = db.get(genreId);
        return Optional.ofNullable(value);
    }

    @Override
    public int deleteByGenreId(final Long genreId) {
        return 0;
    }

    @Override
    public Set<GenreEntity> findAll() {
        return new HashSet<>(db.values());
    }
}
