package me.backendj.lboard.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.backendj.lboard.posts.dto.PostsSaveDto;
import me.backendj.lboard.posts.dto.PostsUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PostsApiControllerTest {
    private static final String API_V_1_POSTS = "/api/v1/posts";

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("전체 포스트 조회")
    void findAll() throws Exception {
        IntStream.range(0, 15).forEach(this::createPosts);

        mockMvc.perform(get("/api/v1/posts")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,DESC")
                .accept(MediaTypes.HAL_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists());
    }

    @Test
    @DisplayName("포스트 단건 조회")
    void findById() throws Exception {
        Posts posts = createPosts(1);
        mockMvc.perform(get("/api/v1/posts/{id}", posts.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(posts.getId()));
    }


    @Test
    @DisplayName("포스트 단건 조회_없는 ID 조회")
    void findById_404() throws Exception {
        mockMvc.perform(get("/api/v1/posts/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Posts 생성 성공")
    void savePosts() throws Exception {
        PostsSaveDto postsDto = PostsSaveDto.builder()
                .title("title -1 ")
                .content("content")
                .author("pej")
                .build();

        mockMvc.perform(post(API_V_1_POSTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    @DisplayName("Posts 생성 Validation 오류 체크")
    void save_400() throws Exception {
        PostsSaveDto postsForm = PostsSaveDto.builder().build();
        mockMvc.perform(post(API_V_1_POSTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsForm)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("포스트 수정")
    void update() throws Exception {

        Posts posts = createPosts(1);

        String modifiedTitle = "수정된 title";
        PostsUpdateDto postsDto = PostsUpdateDto.builder()
                .title(modifiedTitle)
                .content(posts.getContent())
                .build();

        mockMvc.perform(put(API_V_1_POSTS + "/{id}", posts.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(modifiedTitle))
        ;
    }

    @Test
    @DisplayName("삭제 테스트")
    void deletePosts() throws Exception {
        Posts posts = createPosts(1);

        mockMvc.perform(delete(API_V_1_POSTS + "/{id}", posts.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    private Posts createPosts(int index) {
        Posts posts = Posts.builder()
                .title("title" + index)
                .content("content")
                .author("pej")
                .build();
        return postsRepository.save(posts);
    }
}

