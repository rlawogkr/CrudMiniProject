package com.example.crudminiproject.dto;

import com.example.crudminiproject.domain.UserAccount;
import lombok.Getter;

@Getter
public class PostRequest {
    private String title;
    private String content;

    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
