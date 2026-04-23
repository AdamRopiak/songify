package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/artist")
class ArtistController {

    private final SongifyCrudFacade  songifyCrudFacade;

    @GetMapping
    public ResponseEntity<AllArtistDto> findAllArtist(@PageableDefault(page = 0, size = 15) Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(pageable);
        AllArtistDto allArtistDto = new AllArtistDto(allArtists);
        return ResponseEntity.ok(allArtistDto);
    }

    @PostMapping
    public ResponseEntity<ArtistDto> postNewArtist(@RequestBody ArtistUpdateRequestDto requestDto) {
        ArtistDto artistDto = songifyCrudFacade.addArtist(requestDto);
        return ResponseEntity.ok(artistDto);
    }

    @DeleteMapping("/{artistId}")
    ResponseEntity<String> deleteArtistWihtAllAlbumsAndSongs(@PathVariable Long artistId){
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        return ResponseEntity.ok("Artist with id: " + artistId + "has been deleted");
    }

    @PutMapping("/{artistId}/{albumId}")
    ResponseEntity<String> addArtistToAlbum(@PathVariable Long artistId, @PathVariable Long albumId){
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        return ResponseEntity.ok("Artist with id: " + artistId + "has been added to album with id: " + albumId);
    }

    @PatchMapping("/{artistId}")
    ResponseEntity<ArtistDto> updateArtistName(@PathVariable Long artistId,
                                                       @Valid @RequestBody ArtistUpdateRequestDto artistUpdateRequestDto){
        ArtistDto artistDto = songifyCrudFacade.updateArtistById(artistId, artistUpdateRequestDto.artistName());
        return ResponseEntity.ok(artistDto);
    }

    @PostMapping("/album/song")
    ResponseEntity<ArtistDto> addArtistWithDefaultSongAndAlbum(@RequestBody ArtistRequestDto requestDto){
        ArtistDto newArtist = songifyCrudFacade.addArtistWithDefaultAlbumAndSong(requestDto);
        return ResponseEntity.ok(newArtist);
    }

}
