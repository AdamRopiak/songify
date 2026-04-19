package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
class AlbumDeleter {

    private final AlbumRepository albumRepository;

    void deleteAllAlbumsByIds(final Set<Long> albumsIdsToDelete) {
        albumRepository.deleteByAlbumIdIn(albumsIdsToDelete);
    }
}
