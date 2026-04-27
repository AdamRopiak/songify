package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

class InMemoryArtistRepository implements ArtistRepository {
    @Override
    public ArtistEntity save(final ArtistEntity newArtist) {
        return null;
    }

    @Override
    public Set<ArtistEntity> findAll(final Pageable pageable) {
        return Set.of();
    }

    @Override
    public Optional<ArtistEntity> findArtistById(final Long artistId) {
        return Optional.empty();
    }

    @Override
    public int deleteByArtistId(final Long artistId) {
        return 0;
    }
}
