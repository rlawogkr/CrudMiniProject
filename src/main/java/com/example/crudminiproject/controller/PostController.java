package com.example.crudminiproject.controller;

import com.example.crudminiproject.domain.Comment;
import com.example.crudminiproject.domain.UserAccount;
import com.example.crudminiproject.dto.CommentRequest;
import com.example.crudminiproject.service.CommentService;
import org.springframework.ui.Model;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.dto.PostRequest;
import com.example.crudminiproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    // http://localhost:8080/write
    @GetMapping("/write")
    public String write() {
        return "writeForm";
    }

    @PostMapping("/write")
    public String addPost(PostRequest postRequest, Principal principal) {
        postService.addPost(postRequest, principal.getName()); // principal.getName()은 로그인한 사용자의 userId를 반환
        return "redirect:/";
    }

    @GetMapping("/")
    public String postList(Model model, @PageableDefault(size = 3) Pageable pageable) {
        Page<Post> list = postService.findAll(pageable);
        paging(model, pageable, list);

        System.out.println(list);
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        /**
         * 댓글 기능 추가
         */
        List<Comment> comments = commentService.findByPostId(id);
        model.addAttribute("comments", comments);

        return "postView";
    }

    @GetMapping("/posts/{id}/update")
    public String getPostUpdate(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);

        return "updateForm";
    }

    @PostMapping("/posts/{id}/update")
    public String postUpdate(@PathVariable Long id, PostRequest postRequest) {
        postService.update(id, postRequest);
        // model.addAttribute("post", post);

        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);

        return "redirect:/";
    }

    @PostMapping("/posts/{id}/comment")
    public String addComment(@PathVariable Long id, CommentRequest commentRequest, Principal principal) {
        commentRequest.setUserAccount(new UserAccount(principal.getName()));
        commentService.addComment(id, commentRequest);
        return "redirect:/posts/" + id;
    }

    private void paging(Model model, Pageable pageable, Page<Post> list) {
        int startPage;
        int endPage;

        if (list.getTotalPages() < 5) {
            startPage = 1;
            endPage = list.getTotalPages();
        } else if (pageable.getPageNumber() < 3) {
            startPage = 1;
            endPage = 5;
        } else if(pageable.getPageNumber() >= list.getTotalPages() - 3) {   // 현재 페이지가 총 페이지 - 3 보다 크거나 같을 때는
            startPage = list.getTotalPages() - 4;
            endPage = list.getTotalPages();
        }
        else { // 나머지 경우
            startPage = list.getPageable().getPageNumber() - 1;    // 4페이지 보일 때 원래 페이지는 3이므로 1을 빼서 2로 설정
            endPage = list.getPageable().getPageNumber() + 3;   // 원래 페이지인 3에 3을 더해 6 로 설정 (2,3,4,5,6 까지 설정 가능)
        }
        model.addAttribute("list", list);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    }


}
