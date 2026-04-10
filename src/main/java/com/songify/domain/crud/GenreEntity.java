package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEnity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="genre")
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class GenreEntity extends BaseEnity {

    GenreEntity(final String genreName) {
        this.genreName = genreName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_genreid_seq")
    @SequenceGenerator(
            name = "genre_genreid_seq",
            sequenceName = "genre_genreid_seq",
            allocationSize = 1
    )
    @Column(name = "genreid")
    private Long genreId;

    @Column(name = "genrename")
    private String genreName;


}
