package me.backendj.lboard.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
public class PostsApiController {

    private final PostsRepository postsRepository;

    @PostMapping
    public ResponseEntity<Posts> save(@RequestBody @Valid PostsCreateDto postsDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Posts posts = Posts.toEntity(postsDto); //dto to entity
        Posts savedPost = postsRepository.save(posts);

        URI selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(savedPost.getId()).toUri();

        return ResponseEntity.created(selfLink).body(posts);
    }

    @GetMapping
    public ResponseEntity findAll(Pageable pageable, PagedResourcesAssembler<Posts> assembler) {
        Page<Posts> all = postsRepository.findAll(pageable);
        var entityModels = assembler.toModel(all);
        return ResponseEntity.ok(entityModels);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Posts> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Posts> update() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Posts> delete() {
        //update delete code posts > delete > Y
        return ResponseEntity.ok().build();
    }

}
