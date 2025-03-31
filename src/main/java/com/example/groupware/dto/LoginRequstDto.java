package com.example.groupware.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequstDto {
    private String username; // 사용자 이름
    private String password; // 비밀번호

    // 생성자: username과 password를 받아서 객체를 생성할 때 사용
    public LoginRequstDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
