package com.example.groupware.service;

import com.example.groupware.dto.PostRequestDto;
import com.example.groupware.dto.PostResponseDto;
import com.example.groupware.entity.Post;
import com.example.groupware.entity.PostType;
import com.example.groupware.entity.Role;
import com.example.groupware.entity.User;
import com.example.groupware.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final SecurityService securityService;

    @Autowired
    public PostService(PostRepository postRepository, SecurityService securityService) {
        this.postRepository = postRepository;
        this.securityService = securityService;
    }

    // 게시글 저장 (로그인한 사용자 자동 매핑)
    public PostResponseDto savePost(PostRequestDto requestDto) {
        User currentUser = securityService.getCurrentUser(); // JWT에서 현재 로그인된 사용자 가져오기

        // 사용자 역할에 따라 게시글 타입 제한
        if (requestDto.getPostType() == PostType.NOTICE && currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("공지사항은 관리자만 작성할 수 있습니다.");
        }

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), requestDto.getPostType(), currentUser);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시글 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 타입의 게시글 조회
    public List<Post> getPostsByType(PostType postType) {
        return postRepository.findByPostType(postType);
    }

    // 게시글 수정
    public PostResponseDto updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        post.update(title, content);
        Post updatedPost = postRepository.save(post);

        return new PostResponseDto(updatedPost); // 수정된 Post를 PostResponseDto로 반환
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
