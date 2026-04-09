package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEnity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "album")
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class AlbumEntity extends BaseEnity {

    @Id
    @Column(name = "albumid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_albumid_seq")
    @SequenceGenerator(
            name = "album_albumid_seq",
            sequenceName = "album_albumid_seq",
            allocationSize = 1
    )
    private Long albumId;

    @Column(name = "albumtitle")
    private String albumTitle;

    @Column(name = "relase_date")
    private Instant releaseDate;

    @OneToMany
    @JoinColumn(name = "album_albumid")
    private Set<SongEntity> songs = new HashSet<>();

    @ManyToMany(mappedBy = "albums")
    private Set<ArtistEntity> artists = new HashSet<>();
}
