package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryArtistRepository implements ArtistRepository {

    Map<Long, ArtistEntity> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public ArtistEntity save(final ArtistEntity newArtist) {
        Long id= (long) index.getAndIncrement();
        db.put(id, newArtist);
        newArtist.setArtistId(id);
        return newArtist;
    }

    @Override
    public Set<ArtistEntity> findAll(final Pageable pageable) {
        return new HashSet<>(db.values());
    }

    @Override
    public Optional<ArtistEntity> findArtistById(final Long artistId) {
        ArtistEntity artist= db.get(artistId);
        return Optional.ofNullable(artist);
    }

    @Override
    public int deleteByArtistId(final Long artistId) {
        db.remove(artistId);
        return artistId.intValue();
    }
}
