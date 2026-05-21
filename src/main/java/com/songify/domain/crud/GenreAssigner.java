package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreAssigner {
    private final SongRetriever songRetriever;
    private final GenreRetriever genreRetriever;

    void assignDefaultGenreToSong(Long songId) {
        SongEntity song = songRetriever.findSongById(songId);
        GenreEntity genre = genreRetriever.findGenreById(1L);
        song.setGenre(genre);

    }

    void assignGenreToSong(Long gerneId, Long songId) {
        SongEntity song = songRetriever.findSongById(songId);
        GenreEntity genre = genreRetriever.findGenreById(gerneId);
        song.setGenre(genre);
    }
}
