package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
class ArtistRetriever {

    private final ArtistRepository artistRepository;

    Set<ArtistDto> findAllArtist(Pageable pageable) {
        return artistRepository.findAll(pageable)
                .stream()
                .map(artistEntity -> new ArtistDto(
                        artistEntity.getArtistId(),
                        artistEntity.getArtistName()
                ))
                .collect(Collectors.toSet());

    }

    ArtistEntity findArtistById(Long artistId) {
        return artistRepository.findArtistById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("Artist with id: " + artistId + " not found"));
    }
}
