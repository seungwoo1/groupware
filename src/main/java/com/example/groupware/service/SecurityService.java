package com.example.groupware.service;

import com.example.groupware.entity.User;
import com.example.groupware.repository.UserRepository;
import com.example.groupware.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 따로 사용하는 이유는 현재 로그인한 유저를 가져옴. ( 재사용성 있다해서 만들었음 )
@Service
public class SecurityService
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @Autowired
    public SecurityService(UserRepository userRepository, JwtUtil jwtUtil, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.request = request;
    }

    public User getCurrentUser() {
        String token = extractTokenFromRequest();
        if (token != null && jwtUtil.isValid(token)) {
            String username = jwtUtil.extractUsername(token);
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        }
        throw new RuntimeException("유효한 토큰이 없습니다.");
    }

    private String extractTokenFromRequest() {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

