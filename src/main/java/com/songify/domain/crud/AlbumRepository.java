package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumEntityInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface AlbumRepository extends Repository<AlbumEntity, Long> {

    AlbumEntity save(AlbumEntity newAlbum);

    @Query("""
            select a from AlbumEntity a 
            inner join fetch a.songs songs 
            inner join fetch a.artists artists
            where a.albumId = :albumId""")
    Optional<AlbumEntityInfo> findByAlbumAndSongsAndArtists(Long albumId);

  /*  @Query("select a from AlbumEntity a where a.albumId = :albumId")
    Optional<AlbumEntityInfo> findByAlbumAndSongsAndArtists(@Param("albumId") Long albumId);*/
  /*  Optional<com.songify.domain.crud.dto.AlbumEntityInfo> findFirstBy();
  @Query("select a from AlbumEntity a where a.albumId = :albumId order by a.createdOn")
    Optional<AlbumEntityInfo> findByAlbumAndSongsAndArtists(@Param("albumId") Long albumId);*/


}
