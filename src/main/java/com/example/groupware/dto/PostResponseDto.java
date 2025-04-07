package com.example.groupware.dto;

import com.example.groupware.entity.Post;
import com.example.groupware.entity.PostType;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private PostType postType;
    private String authorUsername;  // 작성자 정보 (username만 전달)

    // 기존 생성자에서 Post 객체를 받도록 수정
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postType = post.getPostType();
        this.authorUsername = post.getAuthor().getUsername();  // 작성자의 username 반환
    }
}
