package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class ArtistDeleter {

    private final ArtistRepository artistRepository;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final AlbumDeleter albumDeleter;
    private final SongDeleter songDeleter;

    void deleteArtistByIdWithAlbumsAndSongs(final Long artistId) {
        ArtistEntity artist = artistRetriever.findArtistById(artistId);

        Set<AlbumEntity> artistAlbums = albumRetriever.findAlbumByArtists(artist.getArtistId());

        if(artistAlbums.isEmpty()) {
            log.info("No albums found for artist with id " + artistId);
            artistRepository.deleteByArtistId(artistId);
            return;
        }



        Set<AlbumEntity> albumsWithOnlyOneArtist = artistAlbums.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());

        Set<Long> allSongsIdsFromAllAlbumsWhereWasOnlyThisArtist = albumsWithOnlyOneArtist.stream()
                .flatMap(album -> album.getSongs().stream())
                .map(SongEntity::getId)
                .collect(Collectors.toSet());

        songDeleter.deleteAllSongsById(allSongsIdsFromAllAlbumsWhereWasOnlyThisArtist);

        Set<Long> albumsIdsToDelete = albumsWithOnlyOneArtist.stream()
                .map(AlbumEntity::getAlbumId)
                .collect(Collectors.toSet());
        albumDeleter.deleteAllAlbumsByIds(albumsIdsToDelete);

        artistAlbums.stream()
                .filter(album -> album.getArtists().size() >= 2)
                .forEach(album -> album.removeArtists(artist));

        artistRepository.deleteByArtistId(artistId);

    }
}
