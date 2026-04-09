CREATE TABLE album
(
    albumid     BIGSERIAL PRIMARY KEY,
    albumtitle  VARCHAR(255),
    relase_date TIMESTAMP(6) WITH TIME ZONE
    );