package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class SongRetriever {

    private final SongRepository songRepository;
    private final List<SongEntity> songs = new ArrayList<>();

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongEntity> findAll(Pageable pageable) {
        log.info("Retrieve all songs");
        return songRepository.findAll(pageable);
    }

    public SongEntity findSongById(Long id) {
        return songRepository.findSongEntityById(id)
                .orElseThrow( () -> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    public void existsById(Long id) {
        if(!songRepository.existsById(id)){
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }

    }

    public SongEntity compareSongs() {
        SongEntity song1 = songRepository.findSongEntityById(1L)
                .orElseThrow( () -> new SongNotFoundException("Song with id: " + 1L + " not found"));
        SongEntity song2 = songRepository.findSongEntityById(2L)
                .orElseThrow( () -> new SongNotFoundException("Song with id: " + 1L + " not found"));
        log.info(song1);
        log.info(song2);
        songs.add(song1);
        songs.add(song2);

        log.info(songs.get(0).equals(songs.get(1)));
        return song1;
    }
}
