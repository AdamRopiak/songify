package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class InMemorySongRepository implements SongRepository {

    Map<Long, SongEntity> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public SongEntity save(final SongEntity songEntity) {
        long index = this.index.getAndIncrement();
        db.put(index, songEntity);
        songEntity.setId(index);
      /*  songEntity.setGenre(new GenreEntity(1L, "default"));*/
        return songEntity;
    }

    @Override
    public List<SongEntity> findAll(final Pageable pageable) {
        return db.values().stream().toList();
    }

    @Override
    public Optional<SongEntity> findSongEntityById(final Long id) {
        SongEntity value = db.get(id);
        return Optional.ofNullable(value);
    }

    @Override
    public void deleteById(final Long id) {

    }

    @Override
    public void updateSongById(final Long id, final SongEntity newSong) {

    }

    @Override
    public boolean existsById(final Long id) {
        return false;
    }

    @Override
    public int deleteByIdIn(final Collection<Long> ids) {
        ids.forEach(
                id -> db.remove(id)
        );
        return 0;
    }
}
