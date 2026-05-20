package feature;

import com.songify.SongifyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class HappyPathIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    public MockMvc mockMvc;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    public void f() throws Exception {
//1. when I go to /song then I can see no songs
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));

//2. when I post to /song with Song "Till i collapse" then Song "Til i collapse" is returned with id 1
        mockMvc.perform(post("/songs")
                .content("""
                        {
                          "songName": "Till i collapse",
                          "releaseDate": "2026-05-18T16:07:44.866Z",
                          "songDuration": 0,
                          "songLanguage": "ENGLISH",
                          "genreId": 1
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.songName",is("Till i collapse")))
                .andExpect(jsonPath("$.song.genreDto.genreId",is(1)))
                .andExpect(jsonPath("$.song.genreDto.genreName", is("default")));
        ;

//3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
        mockMvc.perform(post("/songs")
                .content("""
                        {
                          "songName": "Lose Yourself",
                          "releaseDate": "2026-05-18T16:07:44.866Z",
                          "songDuration": 0,
                          "songLanguage": "ENGLISH"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(2)))
                .andExpect(jsonPath("$.song.songName",is("Lose Yourself")))
                .andExpect(jsonPath("$.song.genreDto.genreId",is(1)))
                .andExpect(jsonPath("$.song.genreDto.genreName", is("default")));

//4. when I go to /genre then I can see no genres
        mockMvc.perform(get("/genres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0].genreId", is(1)))
                .andExpect(jsonPath("$.genres[0].genreName", is("default")));

//5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with id 2
        mockMvc.perform(post("/genres")
                .content("""
                        {
                            "genreName": "Rap"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreId", is(2)))
                .andExpect(jsonPath("$.genreName", is("Rap")));

//6. when I go to /song/1 then I can see default genre
        mockMvc.perform(get("/songs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.song.genreDto.genreName", is("default")))
                .andExpect(jsonPath("$.song.genreDto.genreId", is(1)));
    }
}