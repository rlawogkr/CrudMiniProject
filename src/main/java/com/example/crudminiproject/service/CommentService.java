package com.example.crudminiproject.service;

import com.example.crudminiproject.domain.Comment;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.dto.CommentRequest;
import com.example.crudminiproject.repository.CommentRepository;
import com.example.crudminiproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<Comment> findByPostId(Long postId){
        return commentRepository.findAllById(postId);
    }

    public void addComment(Long postId, CommentRequest commentRequest){
        Post post = postRepository.findById(postId).orElse(null);
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setPost(post);
        comment.setUserAccount(commentRequest.getUserAccount());
        commentRepository.save(comment);
    }


}
