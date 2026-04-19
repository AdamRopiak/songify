package com.songify.domain.crud;


import com.songify.domain.crud.dto.ArtistDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


interface ArtistRepository extends Repository<ArtistEntity, Long> {

    ArtistEntity save(ArtistEntity newArtist);

    //@Query("SELECT a FROM ArtistEntity a")
    Set<ArtistEntity> findAll(Pageable pageable);

    @Query("SELECT a FROM ArtistEntity a WHERE a.artistId = :artistId")
    Optional<ArtistEntity> findArtistById(Long artistId);

    @Modifying
    @Query("delete from ArtistEntity a where a.artistId = :artistId")
    int deleteByArtistId(Long artistId);
}
