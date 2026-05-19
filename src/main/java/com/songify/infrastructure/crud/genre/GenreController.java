package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/genres")
class GenreController {

    private final SongifyCrudFacade  songifyCrudFacade;

    @PostMapping
    public ResponseEntity<GenreDto> addGenre(@RequestBody GenreRequestDto requestDto) {
        GenreDto genreDto = songifyCrudFacade.addGenre(requestDto);
        return ResponseEntity.ok(genreDto);
    }

    @GetMapping
    public ResponseEntity<AllGenresDto> gerGenres(){
        Set<GenreDto> genreDto = songifyCrudFacade.getAllGenres();
        AllGenresDto allGenres = new AllGenresDto(genreDto);
        return ResponseEntity.ok(allGenres);
    }
}
