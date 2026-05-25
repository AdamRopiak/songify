package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class SongAsssigner {

    private final AlbumRetriever albumRetriever;
    private final SongRetriever songRetriever;

    public AlbumDto addSongToAlbum(Long albumId, Long songId) {
        AlbumEntity album = albumRetriever.findByAlbumId(albumId);
        SongEntity song = songRetriever.findSongById(songId);
        album.addSongToAlbum(song);
        return new AlbumDto(
                album.getAlbumId(),
                album.getAlbumTitle(),
                album.getSongsIds());

    }
}
