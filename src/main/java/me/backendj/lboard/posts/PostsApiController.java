package me.backendj.lboard.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.backendj.lboard.exception.PostsNotFoundException;
import me.backendj.lboard.posts.dto.PostsSaveDto;
import me.backendj.lboard.posts.dto.PostsUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;

    @GetMapping
    public ResponseEntity selectPosts(Pageable pageable, PagedResourcesAssembler<Posts> assembler) {
        Page<Posts> all = postsRepository.findAll(pageable);
        var entityModels = assembler.toModel(all, PostsEntityModel::of);
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectPost(@PathVariable("id") Long id) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new PostsNotFoundException(id));
        EntityModel<Posts> entityModel = PostsEntityModel.of(post);
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    public ResponseEntity createPost(@RequestBody @Valid PostsSaveDto saveDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Posts post = postsService.save(saveDto);

        WebMvcLinkBuilder linkBuilder = linkTo(PostsApiController.class).slash(post.getId());
        EntityModel entityModel = PostsEntityModel.of(post);
        entityModel.add(linkBuilder.withRel("select-posts"));
        entityModel.add(linkBuilder.withRel("update-posts"));
        return ResponseEntity.created(linkBuilder.toUri()).body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody @Valid PostsUpdateDto updateDto,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Posts post = postsService.update(id, updateDto);
        EntityModel<Posts> entityModel = PostsEntityModel.of(post);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        postsService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
