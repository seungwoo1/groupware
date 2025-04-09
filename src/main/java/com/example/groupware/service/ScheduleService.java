package com.example.groupware.service;

import com.example.groupware.dto.ScheduleRequestDto;
import com.example.groupware.dto.ScheduleResponseDto;
import com.example.groupware.entity.Schedule;
import com.example.groupware.entity.User;
import com.example.groupware.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, User user) {
        Schedule schedule = Schedule.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .user(user)
                .build();

        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saved);
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> getAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::new) // 각 Schedule → ScheduleResponseDto로 변환
                .collect(Collectors.toList()); // 그걸 List로 다시 수집
    }

    // 단일 일정 조회
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, User user) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다"));

        if (!schedule.getUser().getId().equals(user.getId())){
            throw new SecurityException("해당 일정을 수정할 권환이 없습니다.");
        }
        // entity에서 설계함
        schedule.update(requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getStartDate(),
                requestDto.getEndDate());
        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제
    public void deleteSchedule(Long id, User user) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getUser().getId().equals(user.getId())){
            throw new SecurityException("해당 일정을 삭제할 권환이 없습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
