package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends Repository<SongEntity, Long> {
    SongEntity save(SongEntity songEntity);

    List<SongEntity> findAll();

    Optional<SongEntity> findSongEntityById(Long id);

    void deleteById(Long id);

    @Modifying
    @Query("UPDATE SongEntity s SET s.songName = :#{#newSong.songName}, s.artistName = :#{#newSong.artistName} WHERE s.id =:id")
    void updateSongById(Long id, SongEntity newSong);

    boolean existsById(Long id);
}
