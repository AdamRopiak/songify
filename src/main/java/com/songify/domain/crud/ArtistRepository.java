package com.songify.domain.crud;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import org.springframework.data.domain.Pageable;
import java.util.Set;


interface ArtistRepository extends Repository<ArtistEntity, Long> {

    ArtistEntity save(ArtistEntity newArtist);

    //@Query("SELECT a FROM ArtistEntity a")
    Set<ArtistEntity> findAll(Pageable pageable);
}
