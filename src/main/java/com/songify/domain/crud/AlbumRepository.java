package com.songify.domain.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface AlbumRepository extends Repository<AlbumEntity, Long> {

    AlbumEntity save(AlbumEntity newAlbum);

    Optional<AlbumEntity> findByAlbumId(Long albumId);

}
