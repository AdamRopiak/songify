package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongUpdater {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;


    public void updateSongById(Long id, SongEntity newSong) {
        songRepository.updateSongById(id, newSong);
    }

/*
    public SongEntity updatePartiallySongById(Long id, SongEntity songFromRequest) {
        SongEntity songToUpdate = songRetriever.findSongById(id);
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if (songFromRequest.getSongName() != null) {
            builder.songName((songFromRequest.getSongName()));
            log.info("Partially update song");
        } else {
            builder.songName(songToUpdate.getSongName());
            log.info("Partially update artist");
        }
        if (songFromRequest.getArtistName() != null) {
            builder.artistName((songFromRequest.getArtistName()));
        } else {
            builder.artistName(songToUpdate.getArtistName());
        }
        SongEntity toSave = builder.build();
        updateSongById(id, toSave);
        return toSave;
    }
*/
}
