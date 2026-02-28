package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, SongEntity> database = new HashMap<>(Map.of(
            1, new SongEntity("Shawn Menes song", "Shawn Mendes"),
            2, new SongEntity("Rihiana kap kap", "Rhianna"),
            3, new SongEntity("Shawn Menes song2", "Metallica"),
            4, new SongEntity("Rihiana kap kap2", "Kapuś")
    ));

    public SongEntity saveToDatabase(SongEntity newSong) {
        database.put(database.size() + 1, newSong);
        return  newSong;
    }

    public Map<Integer, SongEntity> findAll() {
        return  database;
    }
}
