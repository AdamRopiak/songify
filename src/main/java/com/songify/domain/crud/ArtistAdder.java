package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;
    private final AlbumAdder albumAdder;
    private final SongAdder songAdder;




    ArtistDto addArtist(String artistName) {
        ArtistEntity newArtist = new ArtistEntity(artistName);
        ArtistEntity savedArtist = artistRepository.save(newArtist);
        return new ArtistDto(savedArtist.getArtistId(), savedArtist.getArtistName());
    }

    ArtistDto addArtistWithDefaultAlbumAndSong(final ArtistRequestDto artistRequestDto) {
        String artistName = artistRequestDto.artistName();
        ArtistEntity save = saveArtistWithDefaultAlubAndSong(artistName);
        return new ArtistDto(save.getArtistId(), save.getArtistName());
    }

    private ArtistEntity saveArtistWithDefaultAlubAndSong(final String artistName) {
        AlbumEntity album = albumAdder.addAlbumWithSong(UUID.randomUUID().toString(), LocalDateTime.now().toInstant(ZoneOffset.UTC));

        SongEntity songEntity = songAdder.addDefaultSongToDefaultAlbum(new SongRequestDto(UUID.randomUUID().toString(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                0L,
                SongLanguageDto.OTHER));

        ArtistEntity newArtist = new ArtistEntity(artistName);
        album.addSongToAlbum(songEntity);
        newArtist.addAlbum(album);

        return artistRepository.save(newArtist);

    }
}
