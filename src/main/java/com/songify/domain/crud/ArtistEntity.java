package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEnity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="artist")
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class ArtistEntity extends BaseEnity{

    ArtistEntity(String artistName) {
        this.artistName = artistName;
    }

    @Id
    @Column(name = "artistid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_artistid_seq")
    @SequenceGenerator(
            name = "artist_artistid_seq",
            sequenceName = "artist_artistid_seq",
            allocationSize = 1
    )
    private Long artistId;

    @Column(name = "artistname", nullable = false)
    private String artistName;

    @ManyToMany
    private Set<AlbumEntity> albums = new HashSet<>();
}
