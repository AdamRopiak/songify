package com.songify.domain.crud;

import org.apache.logging.log4j.simple.internal.SimpleProvider;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

interface AlbumRepository extends Repository<AlbumEntity, Long> {

    AlbumEntity save(AlbumEntity newAlbum);

    Optional<AlbumEntity> findByAlbumId(Long albumId);

    @Query("""
        select a from AlbumEntity a 
        inner join a.artists artists 
        where artists.artistId = :artistId
                """)
    Set<AlbumEntity> findAlbumByArtistId(@Param("artistId") Long artistId);

    @Modifying
    @Query("delete from AlbumEntity a where a.albumId in :albumIds")
    int deleteByAlbumIdIn(Collection<Long> albumIds);

    @Query("SELECT a FROM AlbumEntity a WHERE a.albumId = :albumId")
    Optional<AlbumEntity> findAlbumById(Long albumId);

    Set<AlbumEntity>findAllAlbums();

}


