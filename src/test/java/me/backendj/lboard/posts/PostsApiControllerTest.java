package me.backendj.lboard.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Posts 생성")
    void save() throws Exception {
        PostsCreateDto postsForm = PostsCreateDto.builder()
                .title("title")
                .content("content")
                .author("pej")
                .build();

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsForm)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Posts 생성 Validation 오류 체크")
    void save_400() throws Exception {
        PostsCreateDto postsForm = PostsCreateDto.builder().build();
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postsForm)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
