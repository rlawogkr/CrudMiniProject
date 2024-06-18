package com.example.crudminiproject.repository;

import com.example.crudminiproject.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
