CREATE TABLE artist_albums
(
    albums_albumid   BIGINT NOT NULL,
    artists_artistid BIGINT NOT NULL,
    CONSTRAINT pk_artist_albums PRIMARY KEY (albums_albumid, artists_artistid)
);

ALTER TABLE artist_albums
    ADD CONSTRAINT fk_artalb_on_album_entity FOREIGN KEY (albums_albumid) REFERENCES album (albumid);

ALTER TABLE artist_albums
    ADD CONSTRAINT fk_artalb_on_artist_entity FOREIGN KEY (artists_artistid) REFERENCES artist (artistid);