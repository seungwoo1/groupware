package com.example.groupware.repository;

import com.example.groupware.entity.Post;
import com.example.groupware.entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostType(PostType postType); // 게시판 타입별 조회
}
