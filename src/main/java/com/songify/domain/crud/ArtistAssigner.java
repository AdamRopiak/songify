package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
class ArtistAssigner {
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;

    void addArtistToAlbum(final Long artistId, final Long albumId) {
        ArtistEntity artist = artistRetriever.findArtistById(artistId);
        AlbumEntity album = albumRetriever.findByAlbumId(albumId);
        artist.addAlbum(album);
    }
}
