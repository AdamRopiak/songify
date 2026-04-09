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

    @OneToOne
    private GenreEntity genre;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public SongEntity(String songName) {
        this.songName = songName;
    }

}
