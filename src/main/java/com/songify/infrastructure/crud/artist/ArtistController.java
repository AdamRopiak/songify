package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.ArtistRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/artist")
class ArtistController {

    private final SongifyCrudFacade  songifyCrudFacade;

    @PostMapping
    public ResponseEntity<ArtistDto> postNewArtist(@RequestBody ArtistRequestDto requestDto) {
        ArtistDto artistDto = songifyCrudFacade.addArtist(requestDto);
        return ResponseEntity.ok(artistDto);
    }

}
