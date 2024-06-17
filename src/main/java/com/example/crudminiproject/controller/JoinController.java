package com.example.crudminiproject.controller;

import com.example.crudminiproject.jwt.JWTUtil;
import com.example.crudminiproject.service.JoinService;
import com.example.crudminiproject.dto.JoinDTO;
import com.example.crudminiproject.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    // 회원가입
    @PostMapping("/join")
    public String join(@ModelAttribute JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }

}
