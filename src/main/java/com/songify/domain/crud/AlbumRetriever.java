package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Log4j2
@AllArgsConstructor
class AlbumRetriever {

    private AlbumRepository albumRepository;

    AlbumDtoWithArtistsAndSongs findAlbumByIdWithArtistsAndSongs(final Long albumId) {
      AlbumEntity album =  albumRepository.findByAlbumId(albumId)
              .orElseThrow(() -> new AlbumNotFoundException("Album with id " + albumId + " not found"));

       Set<ArtistEntity> artists = album.getArtists();
        Set<SongEntity> songs = album.getSongs();

        AlbumDto albumDto = new AlbumDto(album.getAlbumId(), album.getAlbumTitle());

        Set<ArtistDto> artistsDto = artists.stream()
                .map(artist -> new ArtistDto(
                        artist.getArtistId(),
                        artist.getArtistName()
                ))
                .collect(Collectors.toSet());
        Set<SongDto> songsDto = songs.stream()
                .map(song -> new SongDto(
                        song.getId(),
                        song.getSongName()
                ))
                .collect(Collectors.toSet());

        return new AlbumDtoWithArtistsAndSongs(albumDto, artistsDto, songsDto);
    }

    Set<AlbumEntity> findAlbumByArtists(final Long artistId) {
        return albumRepository.findAlbumByArtistId(artistId);

    }

    AlbumEntity findByAlbumId(final Long albumId) {
        return albumRepository.findAlbumById(albumId)
                .orElseThrow(() -> new ArtistNotFoundException("Album with id: " + albumId + " not found"));
    }
}

