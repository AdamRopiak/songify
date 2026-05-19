CREATE TABLE album
(
    albumid     BIGSERIAL PRIMARY KEY,
    albumtitle  VARCHAR(255),
    release_date TIMESTAMP(6) WITH TIME ZONE
    );