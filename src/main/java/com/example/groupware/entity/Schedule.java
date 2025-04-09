package com.example.groupware.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor // 기본 생성자를 생성해줌
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동 생성
@Builder // 빌더 패턴을 사용
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 자동증가
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY) // N : 1 관계 LAZY: 지연 로딩
    @JoinColumn(name = "user_id") // user_id를 참조
    private User user;

    // 일정 수정 메서드
    public void update(String title, String content, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
