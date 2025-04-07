package com.example.groupware.controller;

import com.example.groupware.entity.User;
import com.example.groupware.repository.UserRepository;
import com.example.groupware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // 관리자 전용 대시보드
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    // 게시판 생기면 해야함
    public String getAdminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    // 관리자 전용 회원조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        // 관리자만 볼 수 있는 사용자 목록
        return userService.getAllUsers();
    }

    // 관리자 전용 회원 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserAdmin(@PathVariable Long id) {
        userService.deleteUserAdmin(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
