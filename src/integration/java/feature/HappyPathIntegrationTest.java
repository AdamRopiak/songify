package feature;

import com.songify.SongifyApplication;
import com.songify.infrastructure.security.jwt.JwtAuthConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private JwtAuthConverter jwtAuthConverter;


    @Test
    public void f() throws Exception {
//1. when I go to /song without jwt token then I can see no songs
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));

//1a. when I go to /song with jwt token then I can see no songs
        mockMvc.perform(get("/songs")
                        .with(authentication(createJwtWithAdminRole()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));

//2. when I post to /song with Song "Till i collapse" then Song "Til i collapse" is returned with id 1
        mockMvc.perform(post("/songs")
                .with(authentication(createJwtWithAdminRole()))
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


//3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
        mockMvc.perform(post("/songs")
                        .with(authentication(createJwtWithAdminRole()))
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

//3a. SECURITY TEST when I post to /song without JWT token with Song "Lose Yourself" then 401 unautorized is returned
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
                        .andExpect(status().isUnauthorized());

//3b. SECURITY TEST when I post to /song with JWT token but with ROLE_USER with Song "Lose Yourself" then 403 forbiden is returned
        mockMvc.perform(post("/songs")
                        .with(authentication(createJwtWithUserRole()))
                        .content("""
                        {
                          "songName": "Lose Yourself",
                          "releaseDate": "2026-05-18T16:07:44.866Z",
                          "songDuration": 0,
                          "songLanguage": "ENGLISH"
                        }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

//4. when I go to /genre then I can see no genres
        mockMvc.perform(get("/genres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0].genreId", is(1)))
                .andExpect(jsonPath("$.genres[0].genreName", is("default")));

//5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with id 2
        mockMvc.perform(post("/genres")
                        .with(authentication(createJwtWithAdminRole()))
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

//7. when I put to /song/1/genre/2 then Genre with id 2 ("Rap") is added to Song with id 1 ("Til i collapse")
        mockMvc.perform(put("/songs/1/genres/2")
                        .with(authentication(createJwtWithAdminRole()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Updated")));

//8. when I go to /song/1 then I can see "Rap" genre
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.genreDto.genreName", is("Rap")));
//9. when I put to /song/2/genre/1 then Genre with id 1 ("Rap") is added to Song with id 2 ("Lose Yourself")
        mockMvc.perform(put("/songs/2/genres/2")
                        .with(authentication(createJwtWithAdminRole()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Updated")));
//10. when I go to /albums then I can see no albums
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allAlbums", empty()));

//11. when I post to /albums with Album "EminemAlbum1" and Song with id 1 then Album "EminemAlbum1" is returned with id 1
        mockMvc.perform(post("/albums")
                        .with(authentication(createJwtWithAdminRole()))
                .content("""
                        {
                          "albumTitle": "EminemAlbum1",
                          "releaseDate": "2026-05-24T17:16:16.680Z",
                          "songIds": [
                           1
                          ]
                        }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId", is(1)))
                .andExpect(jsonPath("$.albumTitle", is("EminemAlbum1")))
                .andExpect(jsonPath("$.songsIds", containsInAnyOrder(1)));

//12. when I go to /albums/1 then I can not see any albums because there is no artist in system
        mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Album with id 1 not found")))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")));

//13. when I post to /artists with Artist "Eminem" then Artist "Eminem" is returned with id 1
        mockMvc.perform(post("/artists")
                        .with(authentication(createJwtWithAdminRole()))
                        .content("""
                                {
                                        "artistName": "Eminem"
                                }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistId", is(1)))
                .andExpect(jsonPath("$.artistName", is("Eminem")));

// 14. when I put to /artists/1/albums/1 then Artist with id 1 ("Eminem") is added to Album with id 1 ("EminemAlbum1")
        mockMvc.perform(put("/artists/1/albums/1")
                        .with(authentication(createJwtWithAdminRole()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Artist with id: 1 has been added to album with id: 1")));

//15.  when I go to /albums/1 then I can see album with single song with id 1 and single artist with id 1
        mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(1)))
                .andExpect(jsonPath("$.artists[*].artistId", containsInAnyOrder(1)))
                .andExpect(jsonPath( "$.artists", hasSize(1)));

//16. when I put to /albums/1/songs/2 then Song with id 2 ("Lose Yourself") is added to Album with id 1 ("EminemAlbum1")
        mockMvc.perform(put("/albums/1/songs/2")
                        .with(authentication(createJwtWithAdminRole()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albumId", is(1)))
                .andExpect(jsonPath("$.albumTitle", is("EminemAlbum1")))
                .andExpect(jsonPath("$.songsIds[*]", containsInAnyOrder(1,2)));

//17. when I go to /albums/1 then I can see album with 2 songs (id1 and id2)
        mockMvc.perform(get("/albums/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.artists[*].artistId", containsInAnyOrder(1)));
    }

    private JwtAuthenticationToken createJwtWithAdminRole(){
        Jwt jwt = Jwt.withTokenValue("123")
                .claim("email", "polishmilk@o2.pl")
                .header("alg", "none")
                .build();
        return jwtAuthConverter.convert(jwt);
    }


    private JwtAuthenticationToken createJwtWithUserRole(){
        Jwt jwt = Jwt.withTokenValue("123")
                .claim("email", "John@gmail.com")
                .header("alg", "none")
                .build();
        return jwtAuthConverter.convert(jwt);
    }

}