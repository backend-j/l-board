package me.backendj.lboard.posts;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.backendj.lboard.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String author; //이 후 객체로 변경

    private boolean deleted;


    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static Posts toEntity(PostsCreateDto form) {
        return Posts.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .author(form.getAuthor())
                .build();
    }
}
