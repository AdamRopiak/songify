package com.songify.domain.crud.song;

import com.songify.domain.crud.song.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongCrudFacade {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public List<SongDto> findAll(Pageable pageable) {
        return songRetriever.findAll(pageable)
                .stream()
                .map(song -> SongDto.builder()
                        .id(song.getId())
                        .songName(song.getSongName())
                        .build())
                .toList();
    }

    public SongDto findSongDtoById(Long id) {
        SongEntity song = songRetriever.findSongById(id);
        return SongDto.builder()
                .id(song.getId())
                .songName(song.getSongName())
                .build();
    }

    public SongDto addSong(final SongDto newSong) {
        String name = newSong.songName();
        SongEntity validatedAndReadyToSaveSong = new SongEntity(name);
        SongEntity addedSong = songAdder.addSong(validatedAndReadyToSaveSong);
        return SongDto.builder()
                .id(addedSong.getId())
                .songName(addedSong.getSongName())
                .build();
    }

    public void deteById(Long id){
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
}
