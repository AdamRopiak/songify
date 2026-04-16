package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEnity;
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
class SongEntity extends BaseEnity {

    public SongEntity(String songName) {
        this.songName = songName;
    }

    SongEntity(String songName, Instant releaseDate, Long duration, SongLanguage language) {
        this.songName = songName;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.language = language;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_id_seq")
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String songName;

    private Instant releaseDate;

    private Long duration;

    @OneToOne(fetch = FetchType.LAZY)
    private GenreEntity genre;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;



}
