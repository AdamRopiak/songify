package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumEntityInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@AllArgsConstructor
class AlbumRetriever {

    private AlbumRepository albumRepository;

    AlbumEntityInfo findByAlbumAndSongsAndArtists(final Long albumId) {
      return albumRepository.findByAlbumAndSongsAndArtists(albumId)
             .orElseThrow(() -> new AlbumNotFoundException("Album with id " + albumId + " not found"));
    /*   Set<ArtistEntity> artists = album.getArtists();
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

        return new AlbumDtoWithArtistsAndSongs(albumDto, artistsDto, songsDto);*/
    }
}
