package com.example.crudminiproject.controller;

import com.example.crudminiproject.dto.JoinDTO;
import com.example.crudminiproject.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    // 회원가입
    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDto, RedirectAttributes redirectAttributes){
        joinService.joinProcess(joinDto);
        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 redirect
    }

}
