package com.example.crudminiproject.service;


import com.example.crudminiproject.domain.Comment;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.domain.UserAccount;
import com.example.crudminiproject.dto.PostRequest;
import com.example.crudminiproject.repository.CommentRepository;
import com.example.crudminiproject.repository.PostRepository;
import com.example.crudminiproject.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserAccountRepository userAccountRepository;

    public void addPost(PostRequest postRequest) {
        Post post = new Post(postRequest.getTitle(), postRequest.getContent());
        postRepository.save(post);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByIdDesc(pageable);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void update(Long postId, PostRequest postRequest) {
        Post post = new Post(postId, postRequest.getTitle(), postRequest.getContent());
        postRepository.save(post);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

}