package me.backendj.lboard.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class PostsTest {

    @Test
    @DisplayName("엔티티 생성 테스트")
    void builder() {
        assertThatCode(() -> Posts.builder().build()).doesNotThrowAnyException();
    }


    @Test
    @DisplayName("수정 테스트")
    void update() {
        //given
        Posts posts = createPosts();

        //when
        String modifiedTitle = "제목 수정";
        posts.update(modifiedTitle, posts.getContent());

        //then
        assertThat(posts.getTitle()).isEqualTo(modifiedTitle);

    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        Posts posts = createPosts();

        posts.delete();

        assertThat(posts.isDeleted()).isTrue();
    }


    private Posts createPosts() {
        return Posts.builder()
                .title("title -1")
                .content("content-1")
                .author("ej")
                .build();
    }
}
