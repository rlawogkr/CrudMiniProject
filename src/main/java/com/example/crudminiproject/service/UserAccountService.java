package com.example.crudminiproject.service;

import com.example.crudminiproject.domain.UserAccount;
import com.example.crudminiproject.dto.UserAccountRequest;
import com.example.crudminiproject.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void join(UserAccountRequest request) {
        UserAccount user = UserAccount.builder()
                .userId(request.getUserId())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();
        userAccountRepository.save(user);
    }

    public UserAccount login(UserAccountRequest request) {
        String userId = request.getUserId();
        Optional<UserAccount> getUser = userAccountRepository.findByUserId(userId);
        if (getUser.isPresent()) {
            UserAccount user = getUser.get();
            if (user.getPassword().equals(request.getPassword())) {
                return user;
            }
        }
        return null;
    }
}