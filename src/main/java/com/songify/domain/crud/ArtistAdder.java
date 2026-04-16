package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class ArtistAdder {

    ArtistRepository artistRepository;

    ArtistDto addArtist(String artistName) {
        ArtistEntity newArtist = new ArtistEntity(artistName);
        ArtistEntity savedArtist = artistRepository.save(newArtist);
        return new ArtistDto(savedArtist.getArtistId(), savedArtist.getArtistName());
    }

}
