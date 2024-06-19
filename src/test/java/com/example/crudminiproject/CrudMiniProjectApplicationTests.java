package com.example.crudminiproject;

import com.example.crudminiproject.domain.Comment;
import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.domain.UserAccount;
import com.example.crudminiproject.repository.CommentRepository;
import com.example.crudminiproject.repository.PostRepository;
import com.example.crudminiproject.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@SpringBootTest
class CrudMiniProjectApplicationTests {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // BCryptPasswordEncoder 주입

    private final int NUM_USERS = 5; // 생성할 사용자 수

    @Test
    @Rollback(value = false) // 롤백하지 않도록 설정
    void setUp() {
        // 여러 개의 사용자 생성
        for (int i = 1; i <= NUM_USERS; i++) {
            UserAccount user = new UserAccount();
            user.setUserId("user" + i);
            user.setPassword(passwordEncoder.encode("password" + i));
            userAccountRepository.save(user);

            // 포스트 생성
            Post post = new Post();
            post.setTitle("Post " + i);
            post.setContent("Content of Post " + i);
            post.setUserAccount(user); // 해당 사용자와 연관 관계 설정
            postRepository.save(post);

            // 댓글 생성
            Comment comment = new Comment();
            comment.setContent("Comment on Post " + i + " by User " + i);
            comment.setUserAccount(user); // 해당 사용자와 연관 관계 설정
            comment.setPost(post); // 해당 포스트와 연관 관계 설정
            comment.setCreatedDate(LocalDateTime.now());
            commentRepository.save(comment);
        }
    }
}
