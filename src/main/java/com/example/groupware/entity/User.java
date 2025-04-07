package com.example.groupware.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA에서 이 클래스가 데이터베이스 테이블과 매핑된다는 것을 의미
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter // Lombok이 자동으로 getter 메서드를 생성해 줌
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@Table(name = "users")
public class User {

    @Id // 기본 키(PK) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 값 설정 (MySQL에서는 AUTO_INCREMENT)
    private Long id; // 사용자의 고유 ID

    @Column(nullable = false, unique = true) // username은 반드시 값이 있어야 하고, 중복 불가능
    private String username; // 사용자 아이디 (로그인 ID)

    @Column(nullable = false) // null 값을 허용하지 않음
    private String password; // 비밀번호

    @Column(nullable = false) // 반드시 값이 있어야 함
    private String email; // 이메일

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장 (예: USER, ADMIN)
    private Role role; // 사용자의 역할 (USER 또는 ADMIN)

    // 생성자
    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
