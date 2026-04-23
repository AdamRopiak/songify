package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
class AlbumAdder {

    AlbumRepository albumRepository;
    private final SongRetriever songRetriever;
    private final AlbumRepository repository;

    AlbumDto addAlbumWithSong(Long songId, String albumTitle, Instant releaseDate) {
        SongEntity songById = songRetriever.findSongById(songId);

        AlbumEntity newAlbum = new AlbumEntity();
        newAlbum.setAlbumTitle(albumTitle);
        newAlbum.addSongToAlbum(songById);
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
