package com.example.groupware.dto;

import com.example.groupware.entity.PostType;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private PostType postType;
}
