package com.example.crudminiproject.controller;

import com.example.crudminiproject.domain.UserAccount;
import com.example.crudminiproject.dto.UserAccountRequest;
import com.example.crudminiproject.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;


    @GetMapping("/join")
    public String joinPage() {
        return "joinPage";
    }

    @PostMapping("/join")
    public String joinAction(UserAccountRequest userAccountRequest) {
        userAccountService.join(userAccountRequest);
        log.info("회원가입 {}", userAccountRequest);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    // @PostMapping("/login")
    public String loginAction(HttpServletRequest request, UserAccountRequest userAccountRequest) {
        UserAccount user = userAccountService.login(userAccountRequest);
        if (user == null) {
            return "redirect:/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getUserId());
        session.setMaxInactiveInterval(300);
        log.info("로그인 {}", userAccountRequest);
        return "redirect:/";
    }

    // @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
