package com.example.groupware.service;

import com.example.groupware.dto.UserRequestDto;
import com.example.groupware.entity.Role;
import com.example.groupware.entity.User;
import com.example.groupware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 저장 메소드  // 회원가입 (비밀번호 암호화 적용)
    public User saveUser(UserRequestDto requestDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // DTO에서 User 엔티티로 변환
        User user = new User(requestDto.getUsername(), encodedPassword, requestDto.getEmail(), Role.USER);
        // User를 저장하고 반환
        return userRepository.save(user);
    }

    // 모든 사용자 조회 메소드
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 아이디로 사용자 조회 메소드
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
