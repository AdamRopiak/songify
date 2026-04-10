package com.songify.domain.crud;

import org.springframework.data.repository.Repository;

interface ArtistRepository extends Repository<ArtistEntity, Long> {

    ArtistEntity save(ArtistEntity newArtist);
}
