package com.example.groupware.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor // 자동 생성자
public class ScheduleRequestDto {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
}
