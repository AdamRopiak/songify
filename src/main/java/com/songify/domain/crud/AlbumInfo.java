package com.songify.domain.crud;


import java.time.Instant;
import java.util.Set;

public interface AlbumInfo {
    Long getAlbumId();


    String getAlbumTitle();

    Instant getReleaseDate();

    Set<SongInfo> getSongs();

    Set<ArtistInfo> getArtists();

    interface SongInfo {
        Long getId();

        String getSongName();

        Instant getReleaseDate();

        Long getDuration();

        GenreInfo getGenre();

        interface GenreInfo {
            Long getGenreId();

            String getGenreName();
        }
    }

    interface ArtistInfo {
        Long getArtistId();

        String getArtistName();
    }
}
