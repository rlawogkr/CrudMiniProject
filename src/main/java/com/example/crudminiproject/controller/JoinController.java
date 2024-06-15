package com.example.crudminiproject.controller;

import com.example.crudminiproject.dto.JoinDTO;
import com.example.crudminiproject.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    // 회원가입
    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDto){
        System.out.println(joinDto.getUsername());
        joinService.joinProcess(joinDto);
        return "join success";
    }
//    // 로그인
//    @GetMapping("/login")
//    public String loginProcess(JoinDTO joinDto){
//        System.out.println(joinDto.getUsername());
//        return "login success";
//    }
}
