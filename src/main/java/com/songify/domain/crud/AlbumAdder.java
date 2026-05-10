package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumAdder {

    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;


    AlbumDto addAlbumWithSong(Set<Long> songIds, String albumTitle, Instant releaseDate) {
        //SongEntity songById = songRetriever.findSongById(songIds);
        Set<SongEntity> songs = songIds.stream()
                .map(songRetriever::findSongById)
                .collect(Collectors.toSet());
        AlbumEntity newAlbum = new AlbumEntity();
        newAlbum.setAlbumTitle(albumTitle);
        newAlbum.addSongsToAlbum(songs);
        newAlbum.setReleaseDate(releaseDate);
        AlbumEntity savedAlbum = albumRepository.save(newAlbum);

        return new AlbumDto(savedAlbum.getAlbumId(), savedAlbum.getAlbumTitle());
    }

    AlbumEntity addAlbumWithSong(String albumTitle, Instant releaseDate) {
        AlbumEntity newAlbum = new AlbumEntity();
        newAlbum.setAlbumTitle(albumTitle);
        newAlbum.setReleaseDate(releaseDate);
        return albumRepository.save(newAlbum);

    }
}
