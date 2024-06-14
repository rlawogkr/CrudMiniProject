package com.example.crudminiproject.repository;

import com.example.crudminiproject.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Users, Long> {

    Boolean existsByUsername(String username);
    //username을 받아 DB에서 회원을 조회
    Users findByUsername(String username);
}
