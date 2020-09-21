package me.backendj.lboard.posts;

import lombok.*;
import me.backendj.lboard.model.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String author;

    private boolean deleted;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /* 글 수정 */
    public void update(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    /* 글 삭제 */
    public void delete() {
        this.setDeleted(true);
    }

}
