package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class SongifyCrudFacade {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    private final ArtistAdder artistAdder;
    private final ArtistRetriever  artistRetriever;
    private final ArtistDeleter artistDeleter;

    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final AlbumRetriever albumRetriever;

    public ArtistDto addArtist(ArtistRequestDto dto){
        return artistAdder.addArtist(dto.artistName());
    }

    public GenreDto addGenre(GenreRequestDto dto){
        return genreAdder.addGenre(dto.genreName());
    }

    public AlbumDto addAlbum(AlbumRequestDto dto){
        return albumAdder.addAlbumWithSong(dto.songId(),dto.albumTitle(), dto.releaseDate());
    }

    public SongDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRetriever.findAllArtist(pageable);
    }

    public List<SongDto> findAllSongs(Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public SongDto findSongDtoById(Long id) {
        return songRetriever.findSongDtoById(id);
    }

    public AlbumDtoWithArtistsAndSongs findAlbumByIdWithArtistsAndSongs(Long albumId){
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(albumId);
    }

    public void deteteSongById(Long id){
        songRetriever.existsById(id);
        songDeleter.deteById(id);
    }

    public void updateSongById(Long id, SongDto newSong) {
        songRetriever.existsById(id);
        SongEntity songValidatedAndReadyToUpdate = new SongEntity(newSong.songName());
        songUpdater.updateSongById(id, songValidatedAndReadyToUpdate);
    }

    public SongDto updatePartiallySongById(Long id, SongDto songFromRequest) {
        songRetriever.existsById(id);
        SongEntity songFromDatabase = songRetriever.findSongById(id);
        SongEntity toSave = new SongEntity();
        if(songFromRequest.songName() != null) {
            toSave.setSongName(songFromRequest.songName());
        }else{
            toSave.setSongName(songFromDatabase.getSongName());
        }
        songUpdater.updateSongById(id, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .songName(toSave.getSongName())
                .build();
    }

    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId){
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);

    }
}
