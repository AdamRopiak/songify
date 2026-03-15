package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Table(name = "song")
@Getter
@Setter
public class SongEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    String songName;
    @Column(name="artist", nullable = false)
    String artistName;

    public SongEntity() {
    }

    public SongEntity(String songName, String artistName) {
        this.songName = songName;
        this.artistName = artistName;
    }

    public SongEntity(Long id, String songName, String artistName) {
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
    }
}
