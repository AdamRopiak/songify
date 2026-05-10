package com.songify.domain.crud.dto;

import com.songify.domain.crud.dto.SongLanguageDto;
import lombok.Builder;

import java.time.Instant;

@Builder
public record SongRequestDto(String songName,
                      Instant releaseDate,
                      Long songDuration,
                      SongLanguageDto songLanguage) {}
