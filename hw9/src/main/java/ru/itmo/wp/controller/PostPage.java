package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @PostMapping({"/post", "/post/", "/post/{id}"})
    public String writeCommentPost(@Valid @ModelAttribute("comment") Comment comment,
                                BindingResult bindingResult, @PathVariable(required = false) String id,
                                HttpSession httpSession, Model model) {
        Post post;
        try {
            post = postService.findById(Long.parseLong(id));
        } catch (NumberFormatException ignored) {
            return "redirect:/";
        }
        model.addAttribute("post", post);
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        comment.setUser(getUser(httpSession));
        postService.writeComment(post, comment);
        putMessage(httpSession, "You left new comment");
        return "redirect:/post/" + id;
    }


    @Guest
    @GetMapping({"/post", "/post/", "/post/{id}"})
    public String post(Model model, @PathVariable(required = false) String id) {
        Post post = null;
        model.addAttribute("comment", new Comment());
        try {
            post = postService.findById(Long.parseLong(id));
        } catch (NumberFormatException ignored) {
            // No operations.
        }
        model.addAttribute("post", post);
        return "PostPage";
    }
}
