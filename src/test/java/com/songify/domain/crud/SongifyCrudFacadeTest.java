package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );



    @Test
    @DisplayName("Should add artist Shawn Mendes withid:0 when Shawn Mendes was send")
    public void should_add_artist_shawn_mendes_with_id_zero_when_shawn_mendes_was_send(){
        //given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .artistName("Shawn Mendes")
                .build();
        final Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        //when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);
        //then
        assertThat(response.artistId()).isEqualTo(0);
        assertThat(response.artistName()).isEqualTo("Shawn Mendes");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should add artist Korn withid:0 when Korn was send")
    void should_add_artist_korn_with_id_zero_when_shawn_mendes_was_send(){
        ArtistRequestDto korn = ArtistRequestDto.builder()
                .artistName("Korn")
                .build();
        ArtistDto response = songifyCrudFacade.addArtist(korn);

        assertThat(response.artistId()).isEqualTo(0);
        assertThat(response.artistName()).isEqualTo("Korn");
    }

    @Test
    @DisplayName("Shuold throw exception ArtistNotFound when id=0")
    public void shuold_throw_exception_artist_not_found_when_id_was_one(){
        //given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
        //then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with id: 0 not found");
    }

    @Test
    @DisplayName("Should delete artist by id when he have no albums")
    public void should_delete_artist_by_id_when_he_have_no_albums(){
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
            .artistName("Shawn Mendes")
            .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).artistId();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId));

       songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist with album and songs by id when artist had one album and he was only artist in album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_had_one_album_and_he_was_only_artist_in_album(){
        ArtistRequestDto requestDto = ArtistRequestDto.builder()
                .artistName("Shawn Mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(requestDto).artistId();
        SongRequestDto newSong = SongRequestDto.builder()
                .songName("New Song")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(newSong);
        Long songId = songDto.id();
        AlbumDto newAlbum = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .albumTitle("New Album")
                .songIds(Set.of(songId))
                .build());
        Long albumId = newAlbum.albumId();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);

        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        Throwable throwable = catchThrowable(()->songifyCrudFacade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id: " + songId + " not found");
        Throwable throwable1 = catchThrowable(()->songifyCrudFacade.findAlbumById(albumId));
        assertThat(throwable1).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable1.getMessage()).isEqualTo("Album with id: 0 not found");

    }

    @Test
    public void should_add_album_with_one_song(){
        SongRequestDto newSong = SongRequestDto.builder()
                .songName("New Song")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(newSong);
        AlbumRequestDto album = AlbumRequestDto.builder()
                .songIds(Set.of(songDto.id()))
                .albumTitle("New Album")
                .build();
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);

        assertThat(songifyCrudFacade.findAllAlbums()).isNotEmpty();
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.albumId()));

    }

    @Test
    @DisplayName("Should add song")
    public void should_add_song() {
        //given
        SongRequestDto newSong = SongRequestDto.builder()
                .songName("New Song")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();

        //when
        SongDto songDto = songifyCrudFacade.addSong(newSong);

        //then
        List<SongDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        assertThat(allSongs)
                .extracting(songDto1 -> songDto1.id())
                        .containsExactly(0l);


    }

    @Test
    @DisplayName("Should add artist to album")
    public void should_add_artist_to_album() {
        //given
        ArtistRequestDto requestDto = ArtistRequestDto.builder()
                .artistName("Shawn Mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(requestDto).artistId();
        SongRequestDto newSong = SongRequestDto.builder()
                .songName("New Song")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(newSong);
        Long songId = songDto.id();
        AlbumDto newAlbum = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .albumTitle("New Album")
                .songIds(Set.of(songId))
                .build());
        Long albumId = newAlbum.albumId();
        //when
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        //then
        Set<AlbumEntity> albumsByArtistId = songifyCrudFacade.findAlbumsByArtistId(artistId);
        assertThat(albumsByArtistId)
                .extracting(AlbumEntity::getAlbumId)
                .containsExactly(0L);

    }

    @Test
    @DisplayName("Should return album by id")
    public void should_return_album_by_id() {
        SongRequestDto newSong = SongRequestDto.builder()
                .songName("New Song")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(newSong);
        Long songId = songDto.id();
        AlbumDto newAlbum = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .albumTitle("New Album")
                .songIds(Set.of(songId))
                .build());
        Long albumId = newAlbum.albumId();

        AlbumDto albumById = songifyCrudFacade.findAlbumById(0L);

        assertThat(albumById)
                .isEqualTo(
                        new AlbumDto(albumId, "New Album")
                );

    }

    @Test
    @DisplayName("Should throw exception AlbumNotFound when album id not exist")
    public void should_throw_exception_when_album_not_found_by_id() {

        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();

        Throwable throwable = catchThrowable(()-> songifyCrudFacade.findAlbumById(0L));

        assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Album with id: 0 not found");
    }

    @Test
    @DisplayName("Should throw exception SongNotFound when album id not exist")
    public void should_throw_exception_when_song_not_found_by_id() {
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).isEmpty());

        Throwable throwable = catchThrowable(()-> songifyCrudFacade.findSongDtoById(0L));

        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id: 0 not found");

    }

/*    @Test
    @DisplayName("Should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_when_there_were_more_than_one_artist_in_album() {
        SongRequestDto newSong = SongRequestDto.builder()
                .songName("New Song")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(newSong);
        AlbumRequestDto album = AlbumRequestDto.builder()
                .songIds(Set.of(songDto.id()))
                .albumTitle("New Album")
                .build();
        Long albumId = songifyCrudFacade.addAlbumWithSong(album).albumId();
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .artistName("Shawn Mendes")
                .build();
        ArtistRequestDto artistRequestDto2 = ArtistRequestDto.builder()
                .artistName("Korn")
                .build();
        Long artistId1 = songifyCrudFacade.addArtist(artistRequestDto).artistId();
        Long artistId2 = songifyCrudFacade.addArtist(artistRequestDto2).artistId();
        songifyCrudFacade.addArtistToAlbum(artistId1, albumId);
        songifyCrudFacade.addArtistToAlbum(artistId2, albumId);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(2);

        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId1);

        AlbumDtoWithArtistsAndSongs albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        assertThat(albumByIdWithArtistsAndSongs.artists())
                .extracting("artistId")
                .containsOnly(artistId2);
    }*/

    @Test
    @DisplayName("Should delete artist with albums and songs by id when artist was the only artist in albums")
    public void should_delete_artist_with_albums_and_songs_by_id_when_artist_was_the_only_artist_in_albums() {
        SongRequestDto newSongOneAlbumOne = SongRequestDto.builder()
                .songName("One album one")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto addSongOneAlbumOne = songifyCrudFacade.addSong(newSongOneAlbumOne);
        SongRequestDto newSongTwoAlbumOne = SongRequestDto.builder()
                .songName("Two album one")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto addSongTwoAlbumOne = songifyCrudFacade.addSong(newSongTwoAlbumOne);
        SongRequestDto newSongOneAlbumTwo = SongRequestDto.builder()
                .songName("One album two")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto addSongOneAlbumTwo = songifyCrudFacade.addSong(newSongOneAlbumTwo);
        SongRequestDto newSongTwoAlbumTwo = SongRequestDto.builder()
                .songName("Two album two")
                .songLanguage(SongLanguageDto.OTHER)
                .build();
        SongDto addSongTwoAlbumTwo = songifyCrudFacade.addSong(newSongTwoAlbumTwo);
        AlbumRequestDto albumOne = AlbumRequestDto.builder()
                .songIds(Set.of(addSongOneAlbumOne.id(), addSongTwoAlbumOne.id()))
                .albumTitle("New Album One")
                .build();
        Long albumOneId = songifyCrudFacade.addAlbumWithSong(albumOne).albumId();
        AlbumRequestDto albumTwo = AlbumRequestDto.builder()
                .songIds(Set.of(addSongOneAlbumTwo.id(), addSongTwoAlbumTwo.id()))
                .albumTitle("New Album Two")
                .build();
        Long albumTwoId = songifyCrudFacade.addAlbumWithSong(albumTwo).albumId();
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .artistName("Shawn Mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(artistRequestDto).artistId();
        songifyCrudFacade.addArtistToAlbum(artistId, albumOneId);
        songifyCrudFacade.addArtistToAlbum(artistId, albumTwoId);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumOneId)).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumTwoId)).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllAlbums().size()).isEqualTo(2);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(4);

        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllAlbums()).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();





    }
}