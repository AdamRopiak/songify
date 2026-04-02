package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Entity
@Table(name = "song")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongEntity extends BaseEnity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_id_seq")
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false )
    private Long id;

    @Column(name="name", nullable = false)
    private String songName;
    @Column(name="artist", nullable = false)
    private String artistName;

    private Instant releaseDate;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public SongEntity(String songName, String artistName) {
        this.songName = songName;
        this.artistName = artistName;
    }

}
