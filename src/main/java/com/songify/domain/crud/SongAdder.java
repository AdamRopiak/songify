package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongAdder {

    private final SongRepository songRepository;

    SongEntity addSong(final SongEntity newSong) {
        log.info("Adding new song: " + newSong);
        newSong.setDuration(200L);
        newSong.setReleaseDate(Instant.now());
        SongEntity savedSong = songRepository.save(newSong);
        return savedSong;
    }

}
