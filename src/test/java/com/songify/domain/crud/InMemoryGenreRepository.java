package com.songify.domain.crud;

class InMemoryGenreRepository implements GenreRepository {

    @Override
    public GenreEntity save(final GenreEntity newGenre) {
        return null;
    }

    @Override
    public int deleteByGenreId(final Long genreId) {
        return 0;
    }
}
