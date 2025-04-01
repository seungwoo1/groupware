package com.example.groupware.repository;

import com.example.groupware.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository는 기본적인 CRUD만 제공하기때문 추가
    // username을 기준으로 사용자 조회
    Optional<User> findByUsername(String username);



}
