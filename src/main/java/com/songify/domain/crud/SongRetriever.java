package com.songify.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongRetriever {

    private final SongRepository songRepository;

    List<SongEntity> findAll (Pageable pageable){
        log.info("Retrieve all songs");
        return songRepository.findAll(pageable);
    }

    SongEntity findSongById(Long id){
        return songRepository.findSongEntityById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    void existsById (Long id){
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }

    }

}
