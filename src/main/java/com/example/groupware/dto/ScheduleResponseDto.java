package com.example.groupware.dto;

import com.example.groupware.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

    // 생성자 Entity -> DTO 변환함
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();           // Schedule 객체에서 id 값을 꺼내와서, 현재 DTO의 id에 저장
        this.title = schedule.getTitle();     // title 필드도 마찬가지로 가져와서 DTO에 넣기
        this.content = schedule.getContent(); // 내용도 그대로 복사
        this.startDate = schedule.getStartDate(); // 시작 날짜 복사
        this.endDate = schedule.getEndDate();     // 종료 날짜 복사
    }
}

