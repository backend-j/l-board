package me.backendj.lboard.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @DisplayName("Posts 생성")
    void savePosts() throws Exception {
        PostsCreateDto postsForm = PostsCreateDto.builder()
                .title("title -1 ")
                .content("content")
                .author("pej")
                .build();

        mockMvc.perform(post(API_V_1_POSTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsForm)))
                .andExpect(status().isCreated())
                .andDo(print())
        ;
    }


    @Test
    @DisplayName("Posts 생성 Validation 오류 체크")
    void save_400() throws Exception {
        PostsCreateDto postsForm = PostsCreateDto.builder().build();
        mockMvc.perform(post(API_V_1_POSTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsForm)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("전체 포스트 조회 테스트")
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
                .andExpect(jsonPath("page").exists())
        ;
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
