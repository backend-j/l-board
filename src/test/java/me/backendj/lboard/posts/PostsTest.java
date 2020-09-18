package me.backendj.lboard.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class PostsTest {

    @Test
    @DisplayName("생성 테스트")
    void builder() {
        assertThatCode(() -> Posts.builder().build()).doesNotThrowAnyException();
    }
}
