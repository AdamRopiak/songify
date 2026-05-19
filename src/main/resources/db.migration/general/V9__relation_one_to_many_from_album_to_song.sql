ALTER TABLE song
    ADD album_albumid BIGINT;

ALTER TABLE song
    ADD CONSTRAINT FK_SONG_ON_ALBUMID FOREIGN KEY (album_albumid) REFERENCES album (albumid);