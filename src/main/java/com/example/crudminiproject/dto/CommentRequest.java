package com.example.crudminiproject.dto;

import com.example.crudminiproject.domain.UserAccount;
import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private UserAccount userAccount;
}
