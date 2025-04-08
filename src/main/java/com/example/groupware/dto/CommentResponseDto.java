package com.example.groupware.dto;

import com.example.groupware.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createAt;
    private List<CommentResponseDto> children; // 대댓글 리스트

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getUser().getUsername(); // 작성자 username 받아옴
        this.createAt = comment.getCreatedAt();

        // 대댓글이 있을 경우, 재귀적으로 children 생성
        if (comment.getChildren() != null && !comment.getChildren().isEmpty()) {
            this.children = comment.getChildren().stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
        }

    }
}
