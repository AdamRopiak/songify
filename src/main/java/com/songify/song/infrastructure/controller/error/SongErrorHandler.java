package com.songify.song.infrastructure.controller.error;


import com.songify.song.infrastructure.controller.SongRestController;
import com.songify.song.domain.model.SongNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(assignableTypes = SongRestController.class)
@Log4j2
public class SongErrorHandler {

    @ExceptionHandler(SongNotFoundException.class)
     public ResponseEntity<ErrorSongResponsDto> handleSongNotFoundException(SongNotFoundException exception) {
        log.warn("SongNotFound Exception " + exception.getMessage() + HttpStatus.NOT_FOUND);
        ErrorSongResponsDto errorDeleteSongResponsDto = new ErrorSongResponsDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDeleteSongResponsDto);

    }
}
