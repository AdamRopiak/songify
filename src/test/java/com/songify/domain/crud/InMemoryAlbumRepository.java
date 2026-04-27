package com.songify.domain.crud;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

class InMemoryAlbumRepository implements AlbumRepository{
    @Override
    public AlbumEntity save(final AlbumEntity newAlbum) {
        return null;
    }

    @Override
    public Optional<AlbumEntity> findByAlbumId(final Long albumId) {
        return Optional.empty();
    }

    @Override
    public Set<AlbumEntity> findAlbumByArtistId(final Long artistId) {
        return Set.of();
    }

    @Override
    public int deleteByAlbumIdIn(final Collection<Long> albumIds) {
        return 0;
    }

    @Override
    public Optional<AlbumEntity> findAlbumById(final Long albumId) {
        return Optional.empty();
    }
}
