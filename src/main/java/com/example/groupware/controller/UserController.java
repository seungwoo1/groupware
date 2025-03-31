package com.example.groupware.controller;

import com.example.groupware.dto.LoginRequstDto;
import com.example.groupware.dto.UserRequestDto;
import com.example.groupware.entity.User;
import com.example.groupware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody UserRequestDto requstDto) {
        return userService.saveUser(requstDto); //회원가입
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequstDto loginRequstDto) {
        return userService.login(loginRequstDto); // 로그인 처리 후 JWT 반환
    }


    // 일반 사용자가 자신의 정보만 조회할 수 있도록
    @GetMapping("/me")
    public Map<String, String> getUserInfo(Principal principal) {
        // principal을 통해 로그인된 사용자의 정보 확인
        String username = principal.getName();  // 로그인한 사용자의 이름을 가져옴
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 필요한 정보만 추출하여 Map 형태로 반환
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());

        return userInfo;  // username과 email만 반환
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }
}
