package com.example.crudminiproject.controller;

import org.springframework.ui.Model;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    // 게시글 목록 조회
    @GetMapping
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "posts/list";
    }
    // 게시글 상세 조회
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }
    // 게시글 작성 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/createForm";
    }
    // 게시글 작성
    @PostMapping
    public String create(Post post, @RequestParam Long userId) {
        postService.save(post, userId);
        return "redirect:/posts";
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/editForm";
    }

    // 게시글 수정
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Post post, @RequestParam Long userId) {
        post.setId(id);
        postService.save(post, userId);
        return "redirect:/posts";
    }

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
