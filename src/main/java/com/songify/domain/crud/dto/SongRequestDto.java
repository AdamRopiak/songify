package com.songify.domain.crud.dto;

import com.songify.domain.crud.dto.SongLanguageDto;

import java.time.Instant;

public record SongRequestDto(String songName,
                      Instant releaseDate,
                      Long songDuration,
                      SongLanguageDto songLanguage) {}
