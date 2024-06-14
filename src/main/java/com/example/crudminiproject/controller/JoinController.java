package com.example.crudminiproject.controller;

import com.example.crudminiproject.dto.JoinDTO;
import com.example.crudminiproject.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDto){
        System.out.println(joinDto.getUsername());
        joinService.joinProcess(joinDto);
        return "join success";
    }
}
