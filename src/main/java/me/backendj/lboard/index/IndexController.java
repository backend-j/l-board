package me.backendj.lboard.index;

import lombok.RequiredArgsConstructor;
import me.backendj.lboard.exception.PostsNotFoundException;
import me.backendj.lboard.posts.Posts;
import me.backendj.lboard.posts.PostsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsRepository postsRepository;

    @GetMapping("/")
    public String main(Model model) {
        List<Posts> all = postsRepository.findAllDesc();
        model.addAttribute("posts", all);
        return "main";
    }


    @GetMapping("/posts")
    public String save() {
        return "posts/posts-save";
    }


    @GetMapping("/posts/{id}")
    public String update(@PathVariable Long id, Model model) {
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new PostsNotFoundException(id));
        model.addAttribute("post", post);
        return "posts/posts-update";
    }
}
