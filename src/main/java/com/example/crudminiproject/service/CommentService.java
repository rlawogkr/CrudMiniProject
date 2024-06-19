package com.example.crudminiproject.service;

import com.example.crudminiproject.domain.Comment;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.dto.CommentRequest;
import com.example.crudminiproject.repository.CommentRepository;
import com.example.crudminiproject.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // postId에 해당하는 comment 목록 생성일 기준 내림차순 반환
    public List<Comment> findByPostId(Long postId){
        return commentRepository.findByPostIdOrderByCreatedDateDesc(postId);
    }

    @Transactional
    public void addComment(Long postId, CommentRequest commentRequest){
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new IllegalArgumentException("Post not found with id: " + postId);
        }
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setPost(post);
        comment.setUserAccount(commentRequest.getUserAccount());
        commentRepository.save(comment);
    }


}
