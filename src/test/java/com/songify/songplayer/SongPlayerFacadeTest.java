package com.songify.songplayer;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SongPlayerFacadeTest {

    SongifyCrudFacade songifyCrudFacade = mock(SongifyCrudFacade.class);
    YoutubeHttpClient youtubeHttpClient = mock(YoutubeHttpClient.class);

    SongPlayerFacade songPlayerFacade = new SongPlayerFacade(songifyCrudFacade, youtubeHttpClient);

    @Test
    @DisplayName("Should return success when played song with id")
    void should_return_success_when_played_song_with_id(){
        //given
        when(songifyCrudFacade.findSongDtoById(any())).thenReturn(
                SongDto.builder()
                        .id(1L)
                        .songName("Mokito song")
                        .build()
        );
        when(youtubeHttpClient.playSongByName(any())).thenReturn("success");
        //when
        String result = songPlayerFacade.playSongWithId(1L);
        //then
        assertThat(result).isEqualTo("success");
    }

    @Test
    @DisplayName("Should throw exception when fail to play song with id")
    void should_throw_exception_when_fail_to_play_song_with_id(){
        when(songifyCrudFacade.findSongDtoById(any())).thenReturn(
                SongDto.builder()
                        .id(1L)
                        .songName("mokito")
                        .build()
        );
        when(youtubeHttpClient.playSongByName(any())).thenReturn("Some failure");

        Throwable throwable = catchThrowable(() -> songPlayerFacade.playSongWithId(1L));

        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable.getMessage()).isEqualTo("some error - result failed");
    }

}