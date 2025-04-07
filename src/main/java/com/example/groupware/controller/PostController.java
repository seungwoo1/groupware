package com.example.groupware.controller;

import com.example.groupware.dto.PostRequestDto;
import com.example.groupware.dto.PostResponseDto;
import com.example.groupware.entity.PostType;
import com.example.groupware.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 저장
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto) {
        // PostService에서 PostResponseDto를 반환하도록 수정
        PostResponseDto postResponseDto = postService.savePost(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }

    // 모든 게시글 조회
    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        // getAllPosts()는 Post 객체들을 반환하므로, 이를 PostResponseDto로 변환
        return postService.getAllPosts().stream()
                .map(PostResponseDto::new) // Post 객체 -> PostResponseDto 변환
                .collect(Collectors.toList());
    }

    // 특정 타입의 게시글 조회
    @GetMapping("/type/{postType}")
    public List<PostResponseDto> getPostsByType(@PathVariable PostType postType) {
        // 특정 타입의 게시글을 조회한 후, PostResponseDto로 변환
        return postService.getPostsByType(postType).stream()
                .map(PostResponseDto::new) // Post 객체 -> PostResponseDto 변환
                .collect(Collectors.toList());
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        // PostService에서 PostResponseDto를 반환하도록 수정
        PostResponseDto postResponseDto = postService.updatePost(id, requestDto.getTitle(), requestDto.getContent());
        return ResponseEntity.ok(postResponseDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }
}
