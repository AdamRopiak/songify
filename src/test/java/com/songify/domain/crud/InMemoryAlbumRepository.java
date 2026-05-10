package com.songify.domain.crud;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


class InMemoryAlbumRepository implements AlbumRepository{

    Map<Long, AlbumEntity> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public AlbumEntity save(final AlbumEntity newAlbum) {
        long id= index.getAndIncrement();
        db.put(id, newAlbum);
        newAlbum.setAlbumId(id);
        return newAlbum;
    }

    @Override
    public Optional<AlbumEntity> findByAlbumId(final Long albumId) {
        AlbumEntity value = db.get(albumId);
        return Optional.ofNullable(value);
    }

    @Override
    public Set<AlbumEntity> findAlbumByArtistId(final Long artistId) {
        return db.values().stream()
                .filter(album -> album.getArtists().stream()
                        .anyMatch(artist -> artist.getArtistId().equals(artistId)))
                .collect(Collectors.toSet());
    }

    @Override
    public int deleteByAlbumIdIn(final Collection<Long> albumIds) {
        albumIds.forEach(
                id -> db.remove(id)
        );
        return 0;
    }

    @Override
    public Optional<AlbumEntity> findAlbumById(final Long albumId) {
        AlbumEntity value = db.get(albumId);
        return Optional.ofNullable(value);
    }

    @Override
    public Set<AlbumEntity> findAllAlbums() {
        return new HashSet<>(db.values());
    }
}
