package com.example.groupware.service;

import com.example.groupware.dto.CommentRequestDto;
import com.example.groupware.dto.CommentResponseDto;
import com.example.groupware.entity.Comment;
import com.example.groupware.entity.Post;
import com.example.groupware.entity.User;
import com.example.groupware.repository.CommentRepository;
import com.example.groupware.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// 생략 public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
//    this.commentRepository = commentRepository;
//    this.postRepository = postRepository;
//}
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 핵심: 부모 댓글이 있으면 찾는다!
        Comment parent = null;
        if (requestDto.getParentId() != null) {
            parent = commentRepository.findById(requestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .post(post)
                .user(user)
                .parent(parent)
                .build();

        Comment saved = commentRepository.save(comment);
        return new CommentResponseDto(saved);
    }

    // 게시글의 모든 댓글 조회 (부모 + 자식 포함)
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        List<Comment> parentComments = commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtAsc(postId);
        return parentComments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        comment.updateContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
