package com.songify.songplayer;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;

public class SongPlayerFacade {

    private final SongifyCrudFacade songifyCrudFacade;
    private final YoutubeHttpClient youtubeHttpClient;

    public SongPlayerFacade(SongifyCrudFacade songifyCrudFacade, YoutubeHttpClient youtubeHttpClient) {
        this.songifyCrudFacade = songifyCrudFacade;
        this.youtubeHttpClient = youtubeHttpClient;
    }
    public String playSongWithId(Long id){
        SongDto songDtoById = songifyCrudFacade.findSongDtoById(id);
        String name = songDtoById.songName();
        String result = youtubeHttpClient.playSongByName(name);
        if(result.equals("success")){
            return result;
        }
        throw new RuntimeException("some error - result failed");
    }
}
