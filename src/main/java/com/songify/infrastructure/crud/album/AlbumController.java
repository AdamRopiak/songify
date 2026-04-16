package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.dto.AlbumEntityInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/album")
class AlbumController {

    private final SongifyCrudFacade  songifyCrudFacade;

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumEntityInfo> getAlbumWithArtistsAndSongs(@PathVariable Long albumId){
        AlbumEntityInfo albumByIdWithArtistsAndSogs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumByIdWithArtistsAndSogs);

    }

    @PostMapping
    public ResponseEntity<AlbumDto> postNewArtist(@RequestBody AlbumRequestDto requestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbum(requestDto);
        return ResponseEntity.ok(albumDto);
    }


}
