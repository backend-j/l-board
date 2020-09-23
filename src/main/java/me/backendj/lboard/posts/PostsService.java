package me.backendj.lboard.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.backendj.lboard.exception.PostsNotFoundException;
import me.backendj.lboard.posts.dto.PostsSaveDto;
import me.backendj.lboard.posts.dto.PostsUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;

    public Posts save(PostsSaveDto postsDto) {
        Posts post = modelMapper.map(postsDto, Posts.class);
        return postsRepository.save(post);
    }

    public Posts update(Long id, PostsUpdateDto postsDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new PostsNotFoundException(String.format("posts ID[%s] Not found", id)));
        posts.update(postsDto.getTitle(), postsDto.getContent());
        return posts;
    }

    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new PostsNotFoundException(String.format("posts ID[%s] Not found", id)));
        posts.delete();
    }
}
