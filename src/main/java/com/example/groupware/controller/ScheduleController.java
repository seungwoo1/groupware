package com.example.groupware.controller;

import com.example.groupware.dto.ScheduleRequestDto;
import com.example.groupware.dto.ScheduleResponseDto;
import com.example.groupware.entity.User;
import com.example.groupware.security.UserDetailsImpl;
import com.example.groupware.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto, user);
        return ResponseEntity.ok(responseDto);
    }

    // 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedule());
    }

    // 단일 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @RequestBody ScheduleRequestDto requestDto) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(scheduleService.updateSchedule(id, requestDto, user));
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        scheduleService.deleteSchedule(id, user);
        return ResponseEntity.ok("일정 삭제 완료");
    }



}
