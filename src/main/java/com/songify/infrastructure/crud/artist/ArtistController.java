package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<ArtistDto> postNewArtist(@RequestBody ArtistRequestDto requestDto) {
        ArtistDto artistDto = songifyCrudFacade.addArtist(requestDto);
        return ResponseEntity.ok(artistDto);
    }

}
