package com.example.crudminiproject.service;

import com.example.crudminiproject.domain.Post;
import com.example.crudminiproject.domain.Users;
import com.example.crudminiproject.repository.PostRepository;
import com.example.crudminiproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // postRepository.findAll()을 호출하여 모든 게시글을 조회하고 반환한다.
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // postRepository.findById(id)를 호출하여 id에 해당하는 게시글을 조회하고 반환한다.
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
    }

    // postRepository.save(post)를 호출하여 게시글을 저장하고 반환한다.
    public Post save(Post post, Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        post.setUser(user);
        return postRepository.save(post);
    }

    // postRepository.deleteById(id)를 호출하여 id에 해당하는 게시글을 삭제한다.
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
