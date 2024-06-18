package com.example.crudminiproject.service;


import com.example.crudminiproject.domain.Comment;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.domain.UserAccount;
import com.example.crudminiproject.dto.PostRequest;
import com.example.crudminiproject.repository.CommentRepository;
import com.example.crudminiproject.repository.PostRepository;
import com.example.crudminiproject.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public void addPost(PostRequest postRequest, String userId) {
        UserAccount userAccount = userAccountRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Post post = new Post(postRequest.getTitle(), postRequest.getContent(), userAccount);
        postRepository.save(post);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByIdDesc(pageable);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long postId, PostRequest postRequest) {
        Post post = new Post(postId, postRequest.getTitle(), postRequest.getContent());
        postRepository.save(post);
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findAllById(postId);
    }


}