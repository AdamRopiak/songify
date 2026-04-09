ALTER TABLE song
    ADD genre_genreid BIGINT;

ALTER TABLE song
    ADD CONSTRAINT FK_SONG_ON_GENRE_GENREID FOREIGN KEY (genre_genreid) REFERENCES genre (genreid);