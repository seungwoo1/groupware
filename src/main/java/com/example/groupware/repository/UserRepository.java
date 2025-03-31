package com.example.groupware.repository;

import com.example.groupware.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 기본적으로 JpaRepository에서 제공하는 메소드(저장, 조회, 수정, 삭제)들이 사용 가능
}
