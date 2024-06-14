package com.example.crudminiproject.service;

import com.example.crudminiproject.domain.Users;
import com.example.crudminiproject.dto.CustomUserDetails;
import com.example.crudminiproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService { // Spring Security에서 사용자의 인증 및 권한부여를 위해 사용자 정보를 제공

    private final UserRepository userRepository;


    // DB에서 특정 user를 찾아서 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users userData = userRepository.findByUsername(username);

        if(userData != null){
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
