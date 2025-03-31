package com.example.groupware.service;

import com.example.groupware.dto.LoginRequstDto;
import com.example.groupware.dto.UserRequestDto;
import com.example.groupware.entity.Role;
import com.example.groupware.entity.User;
import com.example.groupware.repository.UserRepository;
import com.example.groupware.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // JwtUtiil 추가함

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    // 사용자 저장 메소드 회원가입 (비밀번호 암호화 적용)
    public User saveUser(UserRequestDto requestDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // DTO에서 User 엔티티로 변환
        User user = new User(requestDto.getUsername(), encodedPassword, requestDto.getEmail(), Role.USER);
        // User를 저장하고 반환
        return userRepository.save(user);
    }

    public String login(LoginRequstDto loginRequstDto) {
        // Optional로 반환되므로, Optional을 통해 값 확인
        User user = userRepository.findByUsername(loginRequstDto.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginRequstDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("유효하지 않은 패스워드");
        }

        // 비밀번호가 맞다면 JWT 토큰 생성
        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

    @Transactional
    public User updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + id));

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 정보 업데이트
        user.setUsername(requestDto.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(requestDto.getEmail());

        return userRepository.save(user);
    }


    // 사용자 이름으로 사용자 찾기
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    ------------------관리자 로직----------------------------
    // 모든 사용자 조회 메소드(관리자만)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 관리자에 의한 유저 삭제
    public void deleteUserAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        userRepository.delete(user);
    }


}
