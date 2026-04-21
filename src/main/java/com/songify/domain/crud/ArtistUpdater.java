package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
class ArtistUpdater {
    private final ArtistRetriever artistRetriever;

    ArtistDto updateArtistNameById(Long artistId, String artistName) {
        ArtistEntity artist = artistRetriever.findArtistById(artistId);
        artist.setArtistName(artistName);
        return new ArtistDto(artist.getArtistId(), artist.getArtistName());
    }
}
