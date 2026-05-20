package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.AlbumInfo;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
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
    public ResponseEntity<AlbumInfo> getAlbumWithArtistsAndSongs(@PathVariable Long albumId){
        AlbumInfo albumByIdWithArtistsAndSogs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumByIdWithArtistsAndSogs);

    }

    @PostMapping
    public ResponseEntity<AlbumDto> postNewArtist(@RequestBody AlbumRequestDto requestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(requestDto);
        return ResponseEntity.ok(albumDto);
    }


}
