package com.example.groupware.repository;

import com.example.groupware.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 (부모 댓글만 조회)
    List<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(Long postId);

    // 특정 댓글의 대댓글 조회
    List<Comment> findByParentId(Long parentId);
}
