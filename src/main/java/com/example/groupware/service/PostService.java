package com.example.groupware.service;

import com.example.groupware.dto.PostRequestDto;
import com.example.groupware.dto.PostResponseDto;
import com.example.groupware.entity.Post;
import com.example.groupware.entity.PostType;
import com.example.groupware.entity.Role;
import com.example.groupware.entity.User;
import com.example.groupware.repository.PostRepository;
import com.example.groupware.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 저장 (로그인한 사용자 자동 매핑)
    public PostResponseDto savePost(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        User author = userDetails.getUser(); // 유저 정보 꺼냄

        if (requestDto.getPostType() == PostType.NOTICE && author.getRole() != Role.ADMIN) {
            throw new RuntimeException("공지사항은 관리자만 작성할 수 있습니다.");
        }

        Post post = new Post(requestDto, author);
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
    public PostResponseDto updatePost(Long id, String title, String content, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 현재 로그인한 사용자와 작성자(author)가 같은지 확인
        if (!post.getAuthor().getUsername().equals(userDetails.getUsername())) {
            throw new RuntimeException("작성자만 게시글을 수정할 수 있습니다.");
        }

        post.update(title, content);
        Post updatedPost = postRepository.save(post);

        return new PostResponseDto(updatedPost); // 수정된 Post를 PostResponseDto로 반환
    }

    // 게시글 삭제
    public void deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 현재 로그인한 사용자와 작성자(author)가 같은지 확인
        if (!post.getAuthor().getUsername().equals(userDetails.getUsername())) {
            throw new RuntimeException("작성자만 게시글을 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
