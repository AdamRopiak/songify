package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.AlbumInfo;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.AlreadyBoundException;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/albums")
class AlbumController {

    private final SongifyCrudFacade  songifyCrudFacade;

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumInfo> getAlbumWithArtistsAndSongs(@PathVariable Long albumId){
        AlbumInfo albumByIdWithArtistsAndSogs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumByIdWithArtistsAndSogs);

    }
    @GetMapping
    public ResponseEntity<AllAlbumsDto> getAllAlbums(){
        Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbums();
        AllAlbumsDto allAlbumsDto = new AllAlbumsDto(allAlbums);
        return ResponseEntity.ok(allAlbumsDto);

    }

    @PostMapping
    public ResponseEntity<AlbumDto> postNewArtist(@RequestBody AlbumRequestDto requestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(requestDto);
        return ResponseEntity.ok(albumDto);
    }

    @PutMapping("/{albumId}/songs/{songId}")
    public ResponseEntity<AlbumDto> addSongToAlbum(@PathVariable Long albumId, @PathVariable Long songId){
        AlbumDto albumDto = songifyCrudFacade.addSongToAlbum(albumId, songId);
        return ResponseEntity.ok(albumDto);
    }




}
