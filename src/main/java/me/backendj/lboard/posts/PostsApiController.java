package me.backendj.lboard.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
public class PostsApiController {

    private final PostsRepository postsRepository;


    @GetMapping
    public ResponseEntity<Posts> findAll(Pageable pageable, PagedResourcesAssembler<Posts> assembler) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Posts> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Posts> save(@RequestBody @Valid PostsCreateDto postsForm, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Posts posts = Posts.toEntity(postsForm); //dto to entity
        postsRepository.save(posts);
        return ResponseEntity.ok().body(posts);
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
