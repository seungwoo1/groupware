package com.example.groupware.repository;

import com.example.groupware.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// 기본적으로 JpaRepository에서 제공하는 메소드(저장, 조회, 수정, 삭제)들이 사용 가능
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository는 기본적인 CRUD만 제공하기때문 추가
    // username을 기준으로 사용자 조회
    Optional<User> findByUsername(String username);



}
