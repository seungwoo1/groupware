package com.example.groupware.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private Long postId;
    private Long parentId;
}
