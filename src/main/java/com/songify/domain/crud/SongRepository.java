package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface SongRepository extends Repository<SongEntity, Long> {
    SongEntity save(SongEntity songEntity);

    @Query("SELECT s FROM SongEntity s")
    List<SongEntity> findAll(Pageable pageable);

    @Query("SELECT s FROM SongEntity s WHERE s.id=:id")
    Optional<SongEntity> findSongEntityById(Long id);

    @Modifying
    @Query("DELETE FROM SongEntity s WHERE s.id=:id")
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE SongEntity s SET s.songName = :#{#newSong.songName} WHERE s.id =:id")
    void updateSongById(Long id, SongEntity newSong);

    boolean existsById(Long id);

    @Modifying
    @Query("delete from SongEntity s where s.id in :ids")
    int deleteByIdIn(Collection<Long> ids);
}
