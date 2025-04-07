package com.example.groupware.config;

import com.example.groupware.security.JwtAuthenticationFilter;
import com.example.groupware.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 이 부분 추가
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 기본설정 (모든 요청허용)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users", "/users/login").permitAll()  // 회원가입 & 로그인 API 허용
                        .requestMatchers("/users/{id}", "/users/me").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // 관리자만 접근 가능
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()  // GET 요청은 누구나 접근 가능
                        .requestMatchers(HttpMethod.POST, "/posts").authenticated()  // POST 요청은 인증된 사용자만 접근 가능
                        .requestMatchers(HttpMethod.PUT, "/posts/**").authenticated()  // PUT 요청은 인증된 사용자만 접근 가능
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").authenticated()  // DELETE 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()  // 그 외 요청은 인증 필요
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // JWT를 사용하므로 세션 관리 비활성화
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);  // JWT 인증 필터 추가

        return http.build();
    }
}
