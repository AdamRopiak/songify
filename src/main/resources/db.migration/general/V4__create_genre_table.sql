CREATE TABLE genre
(
    genreid   BIGSERIAL NOT NULL,
    genrename VARCHAR(255) NOT NULL UNIQUE ,
    CONSTRAINT pk_genre PRIMARY KEY (genreid)
);

INSERT INTO genre (genrename) VALUES ('default');