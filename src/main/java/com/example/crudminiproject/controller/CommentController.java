package com.example.crudminiproject.controller;

import com.example.crudminiproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    public String addComment(@RequestParam Long postId, @RequestParam Long userId, @RequestParam String content) {
        commentService.addComment(postId, userId, content);
        return "Comment added successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "Comment deleted successfully";
    }

}
