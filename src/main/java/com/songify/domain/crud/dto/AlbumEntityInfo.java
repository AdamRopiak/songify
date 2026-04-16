package com.songify.domain.crud.dto;

import java.time.Instant;
import java.util.Set;


public interface AlbumEntityInfo {
    Long getAlbumId();

    String getAlbumTitle();

    Instant getReleaseDate();

    Set<SongEntityInfo> getSongs();

    Set<ArtistEntityInfo> getArtists();


    interface SongEntityInfo {
        Long getId();

        String getSongName();

        Instant getReleaseDate();

        Long getDuration();

        GenreEntityInfo getGenre();

        interface GenreEntityInfo {
            String getGenreName();
        }
    }


    interface ArtistEntityInfo {
        Long getArtistId();

        String getArtistName();
    }
}