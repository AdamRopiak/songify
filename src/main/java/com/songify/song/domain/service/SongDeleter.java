package com.songify.song.domain.service;

import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.SongMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
public class SongDeleter {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public SongDeleter(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }

    public void deteById(Long id) {
        songRetriever.existsById(id);
        log.info("Deleting song: " + id);
        songRepository.deleteById(id);
    }
}
