package com.example.crudminiproject.repository;

import com.example.crudminiproject.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findAllById(Long postId);
}
