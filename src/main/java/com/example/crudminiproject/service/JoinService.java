package com.example.crudminiproject.service;

import com.example.crudminiproject.domain.Users;
import com.example.crudminiproject.dto.JoinDTO;
import com.example.crudminiproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDto){
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);
        if(isExist){
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        Users data = new Users();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password)); // 비밀번호 암호화
        data.setRole("ROLE_ADMIN"); // 모든 회원가입을 한 사람을 admin 권한을 부여.

        userRepository.save(data);
    }


}
