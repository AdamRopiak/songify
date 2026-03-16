package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class SongRetriever {

    private final SongRepository songRepository;

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
}
