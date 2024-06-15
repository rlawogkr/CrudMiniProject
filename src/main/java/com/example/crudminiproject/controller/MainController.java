package com.example.crudminiproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP(Model model){

        // 세션 현재 사용자 아이디
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 세션 현재 사용자 권한
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//        return "Main Controller: " + username + ", Role: "+ role;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("ROLE_USER");

        // 모델에 사용자 정보 추가
        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "main";



    }
}
