package me.backendj.lboard.posts;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostsEntityModel extends EntityModel<Posts> {
    private static WebMvcLinkBuilder selfLinkBuilder = linkTo(PostsApiController.class);

    // self link 생성용 entityModel 생성
    public static EntityModel<Posts> of(Posts posts) {
        Link link = selfLinkBuilder.slash(posts.getId()).withSelfRel();
        return EntityModel.of(posts, link);
    }

    public URI getSelfLink(Long id) {
        return selfLinkBuilder.slash(id).withSelfRel().toUri();
    }

}
